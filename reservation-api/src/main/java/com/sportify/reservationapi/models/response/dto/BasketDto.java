package com.sportify.reservationapi.models.response.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.util.UUID;

@Getter
@Setter
public class BasketDto {
    private UUID id;
    private String branch;
    private String facility;
    private Date date;
    private Time startTime;
    private Time endTime;
    private Double price;

}
