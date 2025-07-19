package com.example.matching_service.controllers;


import com.example.matching_service.entities.Item;
import com.example.matching_service.entities.Status;
import com.example.matching_service.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/matchings")
public class MatchController {

        @Autowired
        private MatchService matchService;

        @Autowired
        private RestTemplate restTemplate;

        // URL du microservice des items (configurée via application.properties si possible)
        private final String ITEM_SERVICE_URL = "http://localhost:8222/api/v1/items";

        @GetMapping("/basic/{itemId}")
        public ResponseEntity<?> getBasicMatches(@PathVariable Long itemId) {
            try {
                if (itemId == null || itemId <= 0) {
                    return ResponseEntity.badRequest().body("Invalid item ID");
                }

                String itemUrl = ITEM_SERVICE_URL + "/" + itemId;
                ResponseEntity<Item> itemResponse = restTemplate.getForEntity(itemUrl, Item.class);

                if (!itemResponse.getStatusCode().is2xxSuccessful()) {
                    return ResponseEntity.status(itemResponse.getStatusCode()).body("Failed to fetch item");
                }

                Item referenceItem = itemResponse.getBody();
                List<Item> matches = matchService.findMatchesBasic(referenceItem);
                return ResponseEntity.ok(matches);

            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
            }
        }

    @GetMapping("/match/{itemId}")
    public ResponseEntity<?> getAiMatches(@PathVariable Long itemId) {
        try {
            if (itemId == null || itemId <= 0) {
                return ResponseEntity.badRequest().body("Invalid item ID");
            }

            // Récupérer l'item depuis le microservice
            String itemUrl = ITEM_SERVICE_URL + "/" + itemId;
            ResponseEntity<Item> itemResponse = restTemplate.getForEntity(itemUrl, Item.class);

            if (!itemResponse.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.status(itemResponse.getStatusCode()).body("Failed to fetch item");
            }

            Item referenceItem = itemResponse.getBody();

            // Appeler le service IA avec l'objet récupéré
            List<Item> matches = matchService.findMatchesUsingAI(referenceItem);

            if (matches.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(matches);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/ai/{itemId}")
    public ResponseEntity<List<Item>> findMatchesWithAI(@PathVariable Long itemId) {
        try {
            // 1. Récupérer l’item de référence depuis le service Item
            String itemUrl = ITEM_SERVICE_URL + "/" + itemId;
            ResponseEntity<Item> itemResponse = restTemplate.getForEntity(itemUrl, Item.class);
            Item referenceItem = itemResponse.getBody();

            if (referenceItem == null) {
                return ResponseEntity.notFound().build();
            }

            // 2. Appeler le service qui appelle FastAPI pour le matching AI
            List<Item> matches = matchService.findMatchesUsingAI(referenceItem);

            // 3. Retourner la réponse avec un header custom
            return ResponseEntity.ok()
                    .header("X-Matching-Algorithm", "AI-Powered")
                    .body(matches);

        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



}

