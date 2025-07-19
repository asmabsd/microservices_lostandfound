package com.example.user_service.services;



import com.example.user_service.entities.User;

import java.util.List;

public interface IUserService {
        List<User> getAllUsers();


        User getUserById(Long id);


        User createUser(User userEntity);

        User updateUser(Long id, User userEntity);

        void deleteUser(Long id);

}