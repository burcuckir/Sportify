package com.sportify.reservationservice.models.request;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.util.UUID;

@Getter
@Setter
public class AddScheduleRequest {

    private UUID facilityId;
    private Date date;
    private Time startTime;
    private Time endTime;
    private Double price;
}
