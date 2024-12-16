package com.sportify.paymentservice.repositories;

import com.sportify.paymentservice.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
