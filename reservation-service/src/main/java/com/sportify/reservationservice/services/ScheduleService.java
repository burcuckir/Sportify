package com.sportify.reservationservice.services;

import com.sportify.reservationservice.entities.Facility;
import com.sportify.reservationservice.entities.Schedule;
import com.sportify.reservationservice.exceptions.FacilityNotFoundException;
import com.sportify.reservationservice.mappers.ScheduleMapper;
import com.sportify.reservationservice.models.request.AddScheduleRequest;
import com.sportify.reservationservice.models.response.ScheduleListResponse;
import com.sportify.reservationservice.repositories.FacilityRepository;
import com.sportify.reservationservice.repositories.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ScheduleService {

    private final Integer MAX_DATE = 5; //TODO:Consul'e taşı

    private final ScheduleRepository scheduleRepository;

    private final FacilityRepository facilityRepository;

    public ScheduleListResponse getScheduleByFacility(UUID facilityId) {
        LocalDate localMaxDate = LocalDate.now().plusDays(MAX_DATE);
        Date maxDate = Date.from(localMaxDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<Schedule> facilities = scheduleRepository.findAllByFacilityIdAndIsActiveAndDateTimeAfter(facilityId, true, maxDate);
        return ScheduleMapper.mapToScheduleResponse(facilities);
    }

    public void addSchedule(AddScheduleRequest request) {
        Facility facility = facilityRepository.findById(request.getFacilityId());
        if (facility == null) {
            throw new FacilityNotFoundException();
        }
        Schedule schedule = Schedule.create(request.getDate(), request.getStartTime(), request.getEndTime(),
                request.getPrice(), facility);
        scheduleRepository.save(schedule);
    }
}
