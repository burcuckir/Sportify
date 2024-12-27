package com.sportify.reservationapi.models.response;

import com.sportify.reservationapi.models.response.dto.ScheduleDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ScheduleListResponse {

    private List<ScheduleDto> schedules;
}
