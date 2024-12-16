package com.sportify.reservationservice.entities;

import com.sportify.reservationservice.enums.ScheduleStatus;
import com.sportify.reservationservice.exceptions.ScheduleExpiredException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

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

    public void validateScheduleAvailability(){
        LocalDateTime now = LocalDateTime.now();
        LocalDate scheduleDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalTime scheduleStartTime = startTime.toLocalTime();

        if (now.toLocalDate().isAfter(scheduleDate) ||
                (now.toLocalDate().isEqual(scheduleDate) && now.toLocalTime().isAfter(scheduleStartTime))) {
            throw new ScheduleExpiredException();
        }
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
