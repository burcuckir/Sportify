package com.sportify.paymentapi.repositories;

import com.sportify.paymentapi.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Transaction findByOrderId(UUID orderId);
}
