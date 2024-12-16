package com.sportify.reservationservice.models.response.dto;

import com.sportify.reservationservice.enums.ScheduleStatus;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.util.UUID;

@Getter
@Setter
public class ScheduleDto {

    private UUID id;
    private Date date;
    private Time startTime;
    private Time endTime;
    private ScheduleStatus status;
    private Double price;
}
