package com.example.reclamation_service.services;


import com.example.reclamation_service.dto.ReclamationRequest;
import com.example.reclamation_service.entities.Reclamation;
import com.example.reclamation_service.repositories.ReclamationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReclamationService {

    private final ReclamationRepository repository;

    public ReclamationService(ReclamationRepository repository) {
        this.repository = repository;
    }

    public Reclamation createReclamation(ReclamationRequest request) {
        Reclamation reclamation = new Reclamation();
        reclamation.setSenderUserEmail(request.getSenderUserEmail());
        reclamation.setTargetUserEmail(request.getTargetUserEmail());
        reclamation.setReason(request.getReason());
        reclamation.setDetails(request.getDetails());
        return repository.save(reclamation);
    }
    public Reclamation updateReclamation(Long id, ReclamationRequest request) {
        Reclamation existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reclamation not found with id: " + id));

        // Ne change pas le senderUserId
        existing.setReason(request.getReason());
        existing.setDetails(request.getDetails());
        existing.setTargetUserEmail(request.getTargetUserEmail());

        return repository.save(existing); // modifie l'existant
    }
    public void deleteReclamation(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Reclamation not found with id: " + id);
        }
        repository.deleteById(id);
    }

    public List<Reclamation> getAll() {
        return repository.findAll();
    }
}
