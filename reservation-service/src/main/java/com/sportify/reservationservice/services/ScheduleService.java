package com.sportify.reservationservice.services;

import com.sportify.reservationservice.entities.Branch;
import com.sportify.reservationservice.entities.Facility;
import com.sportify.reservationservice.entities.Schedule;
import com.sportify.reservationservice.mappers.ScheduleMapper;
import com.sportify.reservationservice.models.request.AddScheduleRequest;
import com.sportify.reservationservice.models.response.ScheduleListResponse;
import com.sportify.reservationservice.repositories.BranchRepository;
import com.sportify.reservationservice.repositories.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ScheduleService {

    @Value("${schedule.max.date}")
    private String maxDate;

    private final ScheduleRepository scheduleRepository;

    private final BranchRepository branchRepository;

    public ScheduleListResponse getScheduleByFacility(UUID facilityId) {
        LocalDate localMaxDate = LocalDate.now().plusDays(Long.parseLong(maxDate));
        Date maxDate = Date.from(localMaxDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<Schedule> facilities = scheduleRepository.findAllByFacilityIdAndIsActiveAndDateTimeAfter(facilityId, true, maxDate);
        return ScheduleMapper.mapToScheduleResponse(facilities);
    }

    @Transactional
    public void addSchedule(AddScheduleRequest request) {
        List<Branch> branches = branchRepository.getBranchesByIsActive(true);

        if (branches.isEmpty())
            return;

        for (Branch x : branches) {
            List<Facility> facilities = x.getFacilities();
            if (facilities.isEmpty())
                continue;

            for (Facility facility : facilities) {
                Schedule schedule = Schedule.create(request.getDate(), request.getStartTime(), request.getEndTime(),
                        request.getPrice(), facility);
                scheduleRepository.save(schedule);
            }
        }
    }
}
