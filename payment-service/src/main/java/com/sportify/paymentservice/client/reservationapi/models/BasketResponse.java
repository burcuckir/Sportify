package com.sportify.paymentservice.client.reservationapi.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class BasketResponse {
    private UUID userId;
    private Double totalPrice;
    private List<BasketDto> basketItems;
}

