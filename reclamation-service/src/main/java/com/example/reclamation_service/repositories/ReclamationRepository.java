package com.example.reclamation_service.repositories;


import com.example.reclamation_service.entities.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {
}

