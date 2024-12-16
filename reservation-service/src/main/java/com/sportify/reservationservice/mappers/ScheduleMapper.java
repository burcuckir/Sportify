package com.sportify.reservationservice.mappers;

import com.sportify.reservationservice.entities.Schedule;
import com.sportify.reservationservice.models.response.ScheduleListResponse;
import com.sportify.reservationservice.models.response.dto.ScheduleDto;

import java.util.ArrayList;
import java.util.List;

public class ScheduleMapper {

    public static ScheduleListResponse mapToScheduleResponse(List<Schedule> schedules) {
        ScheduleListResponse scheduleListResponse = new ScheduleListResponse();
        List<ScheduleDto> scheduleList = new ArrayList<>();
        schedules.forEach(s -> {
            ScheduleDto scheduleDto = new ScheduleDto();
            scheduleDto.setId(s.getId());
            scheduleDto.setDate(s.getDate());
            scheduleDto.setStartTime(s.getStartTime());
            scheduleDto.setEndTime(s.getEndTime());
            scheduleDto.setPrice(s.getPrice());
            scheduleDto.setStatus(s.getStatus());
            scheduleList.add(scheduleDto);
        });
        scheduleListResponse.setSchedules(scheduleList);
        return scheduleListResponse;
    }
}
