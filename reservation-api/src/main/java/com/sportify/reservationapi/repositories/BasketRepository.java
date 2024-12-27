package com.sportify.reservationapi.repositories;

import com.sportify.reservationapi.entities.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BasketRepository extends JpaRepository<Basket, Long> {

    Basket findByUserId(UUID userId);
}
