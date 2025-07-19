package com.example.user_service.repositories;

import com.example.user_service.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface userRepository extends JpaRepository<User, Long> {
User findByEmail( String email);
}