package com.sportify.reservationapi.services;

import com.sportify.reservationapi.models.response.BasketListResponse;

import java.util.UUID;

public interface BasketService {

    BasketListResponse getBasketByUserId(UUID userId);
    void addToBasket(UUID userId, UUID scheduleId);
    void removeBasketItem(UUID userId, UUID basketItemId);
    void cleanBasket();

}
