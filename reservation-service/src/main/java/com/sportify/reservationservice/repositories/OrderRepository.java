package com.sportify.reservationservice.repositories;

import com.sportify.reservationservice.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> getOrdersByUserId(UUID userId);
}
