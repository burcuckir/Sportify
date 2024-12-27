package com.sportify.paymentapi.entities;

import com.sportify.paymentapi.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "transaction")
public class Transaction extends BaseEntity {

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private UUID orderId;

    @Column(nullable = false)
    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    public static Transaction create(UUID userId, UUID orderId, Double amount, PaymentStatus paymentStatus){
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setStatus(paymentStatus);
        transaction.setOrderId(orderId);
        transaction.setUserId(userId);
        return transaction;
    }
}

