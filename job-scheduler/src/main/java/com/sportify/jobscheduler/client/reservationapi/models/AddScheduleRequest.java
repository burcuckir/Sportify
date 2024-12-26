package com.sportify.jobscheduler.client.reservationapi.models;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@Getter
@Setter
public class AddScheduleRequest {
    private Date date;
    private Time startTime;
    private Time endTime;
    private Double price;
}
