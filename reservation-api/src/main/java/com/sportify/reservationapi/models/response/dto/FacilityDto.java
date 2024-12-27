package com.sportify.reservationapi.models.response.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class FacilityDto {

    private UUID id;
    private String name;
}
