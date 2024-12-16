package com.sportify.reservationservice.models.response.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class BranchDto {

    private UUID id;
    private String name;
}
