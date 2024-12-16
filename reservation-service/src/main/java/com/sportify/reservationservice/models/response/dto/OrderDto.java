package com.sportify.reservationservice.models.response.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
public class OrderDto {
    private String Branch;
    private String Facility;
    private Date ScheduleDate;
    private Time ScheduleStartTime;
    private Time ScheduleEndTime;
    private String ScheduleStatus;
    private Double SchedulePrice;
}

