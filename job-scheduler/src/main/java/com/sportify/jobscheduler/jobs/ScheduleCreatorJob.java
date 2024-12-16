package com.sportify.jobscheduler.jobs;

import com.sportify.jobscheduler.client.reservationapi.ReservationApiClient;
import com.sportify.jobscheduler.mappers.JobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class ScheduleCreatorJob {

    @Value("${tennis.facility.ids}")
    private String facilityId;

    @Autowired
    private ReservationApiClient reservationApiClient;

    @Scheduled(cron = "0 0 10-22 * * *")

    public void executeTask() {
        List<UUID> idList = Arrays.stream(facilityId.split(","))
                .map(UUID::fromString)
                .toList();
      // TODO: consul'a ekle.
        for (UUID id : idList) {
            var request = JobMapper.mapToAddScheduleRequest(id);
            reservationApiClient.addSchedule(request);
        }

        System.out.println("ScheduleCreatorJob executed at: " + LocalDateTime.now());
    }
}
