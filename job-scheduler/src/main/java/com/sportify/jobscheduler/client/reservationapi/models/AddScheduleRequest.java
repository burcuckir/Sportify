package com.sportify.jobscheduler.client.reservationapi.models;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;
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
