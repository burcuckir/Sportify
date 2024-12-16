package com.sportify.reservationservice.models.response.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
public class BasketDto {
    private String branch;
    private String facility;
    private Date date;
    private Time startTime;
    private Time endTime;
    private Double price;

}
