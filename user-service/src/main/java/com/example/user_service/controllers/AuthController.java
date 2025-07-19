package com.example.user_service.controllers;

import com.example.user_service.config.JwtUtil;
import com.example.user_service.dto.AuthRequest;
import com.example.user_service.dto.MessageResponse;

import com.example.user_service.dto.AuthResponse;
import com.example.user_service.entities.User;
import com.example.user_service.repositories.userRepository;
import com.example.user_service.services.CustomUserDetailsService;
import com.example.user_service.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/api/v1/users")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private userRepository userRepo;
    @Autowired
    private JwtUtil jwtUtil;
   /* @PostMapping("/login")

   // @RequestMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        logger.info("Tentative de connexion avec l'email : {}", request.getEmail());

        // Vérifier si l'utilisateur existe
        User user = userRepo.findByEmail(request.getEmail());
        if (user == null) {
            logger.error("Utilisateur non trouvé pour l'email : {}", request.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Utilisateur non trouvé pour l'email : " + request.getEmail());
        }
        logger.info("Utilisateur trouvé : email = {}, mot de passe haché = {}", user.getEmail(), user.getPassword());

        // Vérifier manuellement le mot de passe
        boolean passwordMatches = passwordEncoder.matches(request.getPassword(), user.getPassword());
        logger.info("Vérification du mot de passe : {}", passwordMatches);
        if (!passwordMatches) {
            logger.error("Mot de passe incorrect pour l'email : {}", request.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Mot de passe incorrect pour l'email : " + request.getEmail());
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            logger.info("Authentification réussie pour l'email : {}", request.getEmail());
        } catch (BadCredentialsException e) {
            logger.error("Erreur d'authentification pour l'email : {}, message : {}", request.getEmail(), e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Erreur d'authentification : " + e.getMessage());
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        logger.info("UserDetails chargé : username = {}", userDetails.getUsername());
        final String token = jwtUtil.generateToken(userDetails.getUsername());
        logger.info("Token généré : {}", token);

        return ResponseEntity.ok(new AuthResponse(token));
    }*/


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        logger.info("Tentative de connexion avec l'email : {}", request.getEmail());

        // Vérifier si l'utilisateur existe
        User user = userRepo.findByEmail(request.getEmail());
        if (user == null) {
            logger.error("Utilisateur non trouvé pour l'email : {}", request.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Utilisateur non trouvé pour l'email : " + request.getEmail());
        }

        logger.info("Utilisateur trouvé : email = {}, mot de passe haché = {}", user.getEmail(), user.getPassword());

        // Vérification manuelle du mot de passe
        boolean passwordMatches = passwordEncoder.matches(request.getPassword(), user.getPassword());
        logger.info("Correspondance du mot de passe (passwordEncoder.matches) : {}", passwordMatches);

        if (!passwordMatches) {
            logger.error("Mot de passe incorrect pour l'email : {}", request.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Mot de passe incorrect pour l'email : " + request.getEmail());
        }

        // Authentification via Spring Security (utile si tu as bien configuré DaoAuthenticationProvider)
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            logger.info("Authentification Spring Security réussie pour l'email : {}", request.getEmail());
        } catch (BadCredentialsException e) {
            logger.error("Erreur Spring Security : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Erreur d'authentification : " + e.getMessage());
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        final String token = jwtUtil.generateToken(userDetails.getUsername());

        logger.info("Token JWT généré avec succès pour {} : {}", userDetails.getUsername(), token);

        return ResponseEntity.ok(new AuthResponse(token));
    }


    @PutMapping("/premium/{email}")
    public ResponseEntity<?> updatePremiumStatus(@PathVariable String email, @RequestBody Map<String, Boolean> payload) {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        Boolean isPremium = payload.get("premium");
        user.setPremium(isPremium);
        userRepo.save(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/premium")
    public ResponseEntity<Boolean> isUserPremium(@RequestParam String email) {
        User user = userRepo.findByEmail(email);
        if (user != null && Boolean.TRUE.equals(user.getPremium())) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false); // <- pas notFound sinon Angular reçoit une 404
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userRepo.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Email déjà utilisé !"));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");

        User savedUser = userRepo.save(user);
        logger.info("Utilisateur enregistré : email = {}, mot de passe haché = {}", savedUser.getEmail(), savedUser.getPassword());

        return ResponseEntity.ok(Map.of("message", "Utilisateur enregistré avec succès !"));
    }
    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable String email) {
       User  user = userRepo.findByEmail(email);
        return user;
    }


    @Autowired
    private IUserService userService;



    @GetMapping("/list")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/list/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/list/create")
    public User createUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userService.createUser(user);
    }
    @GetMapping("/debug/users")
    public List<Map<String, String>> debugAllUsersPasswords() {
        List<User> users = userRepo.findAll();

        return users.stream()
                .map(user -> Map.of(
                        "email", user.getEmail(),
                        "hashedPassword", user.getPassword(),
                        "isBCrypt", String.valueOf(user.getPassword().startsWith("$2a$"))
                ))
                .toList();
    }




    @PutMapping("/list/update/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }


    @DeleteMapping("/list/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }


    private final Path rootLocation = Paths.get("uploads");
    @PostMapping("/upload-image/{id}")
    public ResponseEntity<String> uploadImage(
            @PathVariable long id,
            @RequestParam("file") MultipartFile file) {
        try {
            // S'assurer que le dossier existe
            if (!Files.exists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }

            // Génération du nom de fichier unique
            String originalFilename = file.getOriginalFilename();
            String filename = UUID.randomUUID() + "_" + originalFilename;

            // Enregistrement du fichier sur le serveur
            Path destinationFile = rootLocation.resolve(filename);
            Files.copy(file.getInputStream(), destinationFile);

            // Mise à jour de l'entité Item avec le nom de la photo
            User user = userService.getUserById(id);
            user.setPhoto(filename);
            userService.updateUser(id, user);

            return ResponseEntity.ok(filename);
        } catch (Exception e) {
            e.printStackTrace(); // Pour voir l'erreur dans la console backend
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload image: " + e.getMessage());
        }
    }private final Path uploadDir = Paths.get("uploads");

    @GetMapping("/uploads/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) {
        try {
            Path file = uploadDir.resolve(imageName);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() && resource.isReadable()) {
                String contentType = Files.probeContentType(file);
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}