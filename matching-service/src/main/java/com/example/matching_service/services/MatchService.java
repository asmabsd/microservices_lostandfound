package com.example.matching_service.services;


import com.example.matching_service.dto.UserDto;
import com.example.matching_service.entities.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.matching_service.entities.Item;
import com.example.matching_service.entities.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import com.example.matching_service.entities.Item;
import com.example.matching_service.entities.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MatchService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${services.item.url}")
    private String itemServiceUrl;

    @Value("${services.ai.url}")
    private String aiServiceUrl;

    public MatchService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public List<Item> findMatchesBasic(Item referenceItem) {
        if (referenceItem == null || referenceItem.getStatus() == null) {
            return Collections.emptyList();
        }

        Status oppositeStatus = referenceItem.getStatus() == Status.LOST ? Status.FOUND : Status.LOST;
        String url = itemServiceUrl + "/search?status=" + oppositeStatus;

        try {
            ResponseEntity<Item[]> response = restTemplate.getForEntity(url, Item[].class);
            Item[] items = response.getBody();

            if (response.getStatusCode().is2xxSuccessful() && items != null) {
                return Arrays.stream(items)
                        .filter(candidate -> isMatching(referenceItem, candidate))
                        .collect(Collectors.toList());
            }
        } catch (Exception ignored) {}

        return Collections.emptyList();
    }
    public List<Item> findMatchesUsingAI(Item referenceItem) {
        if (referenceItem == null || referenceItem.getStatus() == null) {
            return Collections.emptyList();
        }

        // Construire la requête JSON
        Map<String, Object> requestPayload = new HashMap<>();
        requestPayload.put("reference_item_id", referenceItem.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestPayload, headers);

        try {
            ResponseEntity<MatchingResponse> aiResponse = restTemplate.postForEntity(
                    aiServiceUrl + "/ai-match",
                    request,
                    MatchingResponse.class
            );

            if (aiResponse.getStatusCode().is2xxSuccessful() && aiResponse.getBody() != null) {
                return aiResponse.getBody().getMatches();
            }

        } catch (Exception e) {
            // Gérer erreur, fallback...
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    // Méthode utilitaire pour convertir un Item en Map
    private Map<String, Object> convertItemToMap(Item item) {
        Map<String, Object> itemMap = new HashMap<>();
        itemMap.put("id", item.getId());
        itemMap.put("type", item.getType());
        itemMap.put("description", item.getDescription());
        itemMap.put("location", item.getLocation());
        itemMap.put("date", item.getDate() != null ? item.getDate().toString() : null);
        itemMap.put("photo", item.getPhoto()); // Doit déjà être en base64
        itemMap.put("status", item.getStatus().toString());
        itemMap.put("useremail", item.getUseremail());
        return itemMap;
    }
    public String encodeImageToBase64(String imagePath) throws IOException {
        byte[] fileContent = Files.readAllBytes(Paths.get(imagePath));
        return Base64.getEncoder().encodeToString(fileContent);
    }

    public boolean isMatching(Item a, Item b) {
        if (a == null || b == null) return false;

        boolean sameType = a.getType() != null && a.getType().equalsIgnoreCase(b.getType());
        boolean sameLocation = a.getLocation() != null && a.getLocation().equalsIgnoreCase(b.getLocation());

        return sameType && sameLocation;

        // Ajoute cette partie si tu veux filtrer par date :
        /*
        boolean similarDate = a.getDate() != null && b.getDate() != null &&
                Math.abs(ChronoUnit.DAYS.between(a.getDate(), b.getDate())) <= 3;
        return sameType && sameLocation && similarDate;
        */
    }




}
