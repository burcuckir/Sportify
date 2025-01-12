package com.sportify.jobscheduler.mappers;

import com.sportify.jobscheduler.client.reservationapi.models.AddScheduleRequest;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class JobMapper {
    public static AddScheduleRequest mapToAddScheduleRequest(Integer startHour) {

        LocalDateTime now = LocalDateTime.now().plusDays(1)
                    .withHour(startHour)
                    .withMinute(0)
                    .withSecond(0)
                    .withNano(0);

        Date date = Date.from(now.toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Time startTime = Time.valueOf(now.toLocalTime());
        Time endTime = Time.valueOf(now.toLocalTime().plusHours(1));

        AddScheduleRequest addScheduleRequest = new AddScheduleRequest();
        addScheduleRequest.setPrice(200.0);
        addScheduleRequest.setDate(date);
        addScheduleRequest.setStartTime(startTime);
        addScheduleRequest.setEndTime(endTime);

        return addScheduleRequest;
    }
}
