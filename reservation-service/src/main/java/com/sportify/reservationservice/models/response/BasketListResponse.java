package com.sportify.reservationservice.models.response;

import com.sportify.reservationservice.models.response.dto.BasketDto;
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