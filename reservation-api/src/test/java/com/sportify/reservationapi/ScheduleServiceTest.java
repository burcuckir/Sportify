package com.sportify.reservationapi;

import com.sportify.reservationapi.entities.Branch;
import com.sportify.reservationapi.entities.Schedule;
import com.sportify.reservationapi.models.request.AddScheduleRequest;
import com.sportify.reservationapi.repositories.BranchRepository;
import com.sportify.reservationapi.repositories.ScheduleRepository;
import com.sportify.reservationapi.services.schedule.ScheduleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScheduleServiceTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private BranchRepository branchRepository;

    @InjectMocks
    private ScheduleServiceImpl scheduleService;

    @Test
    void addSchedule_shouldDoNothing_whenNoBranchesExist() {
        AddScheduleRequest request = new AddScheduleRequest();
        when(branchRepository.getBranchesByIsActive(true)).thenReturn(new ArrayList<>());

        scheduleService.addSchedule(request);

        verify(scheduleRepository, never()).save(any(Schedule.class));
    }

    @Test
    void addSchedule_shouldDoNothing_whenBranchesHaveNoFacilities() {
        AddScheduleRequest request = new AddScheduleRequest();
        Branch branch = new Branch();
        branch.setFacilities(new ArrayList<>());
        when(branchRepository.getBranchesByIsActive(true)).thenReturn(List.of(branch));

        scheduleService.addSchedule(request);

        verify(scheduleRepository, never()).save(any(Schedule.class));
    }
}
