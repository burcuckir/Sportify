package com.sportify.reservationapi.models.request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AddToBasketRequest {

    private UUID scheduleId;
}
