package com.sportify.reservationapi.services;

import com.sportify.reservationapi.models.response.OrderListResponse;

import java.util.UUID;

public interface OrderService {
    void completeOrder(UUID orderId, UUID userId, Double amount);
    OrderListResponse getOrders(UUID userId);
}
