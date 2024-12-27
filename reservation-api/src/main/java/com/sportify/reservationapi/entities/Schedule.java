package com.sportify.reservationapi.entities;

import com.sportify.reservationapi.enums.ScheduleStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@Entity
@Table(name = "schedule")
public class Schedule extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "facility_id", nullable = false)
    private Facility facility;

    private Date date;

    @Column(name = "start_time")
    private Time startTime;

    @Column(name = "end_time")
    private Time endTime;

    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;

    private Double price;

    private Boolean isActive;

    public void reserved(){
        status = ScheduleStatus.RESERVED;
    }

    public static Schedule create(Date date, Time startTime, Time endTime, double price, Facility facility){
        Schedule schedule = new Schedule();
        schedule.setDate(date);
        schedule.setStartTime(startTime);
        schedule.setEndTime(endTime);
        schedule.setPrice(price);
        schedule.setFacility(facility);
        schedule.setStatus(ScheduleStatus.AVAILABLE);
        schedule.setIsActive(true);
        return schedule;
    }
}
