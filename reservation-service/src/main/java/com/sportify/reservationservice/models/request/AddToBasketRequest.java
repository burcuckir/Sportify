package com.sportify.reservationservice.models.request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AddToBasketRequest {

    private UUID scheduleId;
}
