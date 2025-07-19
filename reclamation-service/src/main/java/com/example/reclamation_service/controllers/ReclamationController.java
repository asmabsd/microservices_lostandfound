package com.example.reclamation_service.controllers;

import com.example.reclamation_service.dto.ReclamationRequest;
import com.example.reclamation_service.entities.Reclamation;
import com.example.reclamation_service.services.CohereService;
import com.example.reclamation_service.services.ReclamationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/reclamations")
public class ReclamationController {

    private final ReclamationService service;
    private final CohereService aiService;

    public ReclamationController(ReclamationService service, CohereService aiService) {
        this.service = service;
        this.aiService= aiService;
    }

    @PostMapping
    public ResponseEntity<Reclamation> create(@RequestBody ReclamationRequest request) {
        return ResponseEntity.ok(service.createReclamation(request));
    }

    @GetMapping
    public ResponseEntity<List<Reclamation>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }


    @PutMapping("/{id}")
    public ResponseEntity<Reclamation> update(
            @PathVariable Long id,
            @RequestBody ReclamationRequest request
    ) {
        return ResponseEntity.ok(service.updateReclamation(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReclamation(@PathVariable Long id) {
        service.deleteReclamation(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }









    @PostMapping("/generate-description")
    public Mono<ResponseEntity<String>> generateDescription(@RequestBody Map<String, String> request) {
        String prompt = request.get("prompt"); // ou "raison", selon ton front

        if (prompt == null || prompt.isBlank()) {
            return Mono.just(ResponseEntity.badRequest().body("Le champ 'prompt' est requis."));
        }
        String promptWithSuffix = prompt + " write a good description about that";

        return aiService.generateDescription(promptWithSuffix)
                .map(description -> ResponseEntity.ok().body(description))
                .onErrorResume(error -> {
                    error.printStackTrace();
                    return Mono.just(ResponseEntity.status(500).body("Erreur lors de la génération."));
                });
    }


}

