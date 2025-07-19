package com.example.payment_service.services;


import com.example.payment_service.entities.Payment;

import com.example.payment_service.repositories.PaymentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService implements PaymentServicee {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${gateway.url}")
    private String gatewayUrl;



    /**
     * Vérifie si un utilisateur est premium via l'API externe
     */
    public boolean isUserPremium(String email) {
        String url = gatewayUrl + "/api/v1/users/is-premium?email={email}";
        ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class, email);
        return Boolean.TRUE.equals(response.getBody());
    }

    /**
     * Crée un paiement
     */
    @Override
    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    /**
     * Récupère un paiement par ID
     */
    @Override
    public Optional<Payment> getPayment(Long id) {
        return paymentRepository.findById(id);
    }

    /**
     * Récupère tous les paiements
     */
    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    /**
     * Met à jour le statut premium d’un utilisateur (appel PATCH à l’API externe)
     */



}

