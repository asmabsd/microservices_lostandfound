package com.example.item_service.controllers;

import com.example.item_service.entities.Item;
import com.example.item_service.entities.Status;
import com.example.item_service.repositories.ItemRepository;
import com.example.item_service.services.ItemService;
import org.apache.tomcat.util.file.ConfigurationSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/api/v1/items")
public class ItemController {

    @Autowired
    private ItemService itemService;


    @Autowired
    private ItemRepository itemRepository;

    @PostMapping
    public ResponseEntity<Item> create(@RequestBody Item item) {
        return ResponseEntity.ok(itemService.createItem(item));
    }

    @GetMapping
    public List<Item> getAll() {
        return itemService.getAllItems();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> update(@PathVariable Long id, @RequestBody Item item) {
        return ResponseEntity.ok(itemService.updateItem(id, item));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Item> updateStatus(@PathVariable Long id, @RequestParam Status status) {
        return ResponseEntity.ok(itemService.updateStatus(id, status));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        Item item = itemRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found")
        );
        return ResponseEntity.ok(item);
    }
    @GetMapping("/search")
    public List<Item> searchByStatus(@RequestParam Status status) {
        return itemRepository.findByStatus(status);
    }
    private final Path rootLocation = Paths.get("uploads");
    @PostMapping("/{id}/upload-image")
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
            Item item = itemService.getItem(id);
            item.setPhoto(filename);
            itemService.updateItem(id, item);

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

