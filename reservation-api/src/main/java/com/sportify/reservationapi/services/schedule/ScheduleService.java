package com.sportify.reservationapi.services.schedule;

import com.sportify.reservationapi.models.request.AddScheduleRequest;
import com.sportify.reservationapi.models.response.ScheduleListResponse;

import java.util.UUID;

public interface ScheduleService {
    ScheduleListResponse getScheduleByFacility(UUID facilityId);
    void addSchedule(AddScheduleRequest request);
}
