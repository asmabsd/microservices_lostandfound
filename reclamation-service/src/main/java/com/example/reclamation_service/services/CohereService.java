package com.example.reclamation_service.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class CohereService {
    private final WebClient webClient;

    public CohereService(@Value("${cohere.api.key}") String apiKey) {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.cohere.ai/v1")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public Mono<String> generateDescription(String prompt) {
        Map<String, Object> body = Map.of(
                "model", "command",
                "prompt", prompt,
                "max_tokens", 100,
                "temperature", 0.7,
                "return_likelihoods", "NONE",
                "truncate", "END"
        );

        return webClient.post()
                .uri("/generate")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    List<Map<String, Object>> generations = (List<Map<String, Object>>) response.get("generations");
                    return (String) generations.get(0).get("text");
                });
    }
}
