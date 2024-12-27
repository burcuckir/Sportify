package com.sportify.reservationapi.models.response;

import com.sportify.reservationapi.models.response.dto.BasketDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class BasketListResponse {

    private UUID userId;
    private Double totalPrice;
    private List<BasketDto> basketItems;
}