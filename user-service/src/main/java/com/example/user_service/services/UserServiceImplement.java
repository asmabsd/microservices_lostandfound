package com.example.user_service.services;

import com.example.user_service.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;
import java.util.List;
import com.example.user_service.repositories.userRepository;


@Service
public class UserServiceImplement implements IUserService {

   @Autowired
   userRepository userRepository;
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }





    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));  // BCrypt ici
        return userRepository.save(user);
    }


    @Override
    public User updateUser(Long id, User user) {
        User existinguser = userRepository.findById(id).orElse(null);
        if (existinguser != null) {
            existinguser.setFirstName(user.getFirstName());
            existinguser.setLastName(user.getLastName());
            existinguser.setCin(user.getCin());
            existinguser.setEmail(user.getEmail());
            existinguser.setPassword(passwordEncoder.encode(user.getPassword()));  // BCrypt ici
            return userRepository.save(existinguser);
        }
        return null;
    }


    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}