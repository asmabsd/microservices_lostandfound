package com.example.payment_service.services;

import com.example.payment_service.entities.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentServicee {

    Payment createPayment(Payment payment);
    Optional<Payment> getPayment(Long id);
    List<Payment> getAllPayments();
}
