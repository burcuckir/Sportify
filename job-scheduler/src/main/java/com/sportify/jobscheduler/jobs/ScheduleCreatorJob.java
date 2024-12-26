package com.sportify.jobscheduler.jobs;

import com.sportify.jobscheduler.client.reservationapi.ReservationApiClient;
import com.sportify.jobscheduler.client.reservationapi.models.AddScheduleRequest;
import com.sportify.jobscheduler.mappers.JobMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class ScheduleCreatorJob {

    private final ReservationApiClient reservationApiClient;
    private static final Logger logger = LoggerFactory.getLogger(ScheduleCreatorJob.class);

    @PostConstruct
    public void runOnStartup() {
        for (int i = 10; i <= 21; i++) {
            AddScheduleRequest request = JobMapper.mapToAddScheduleRequest(i);
            reservationApiClient.addSchedule(request);
        }
    }

    @Scheduled(cron = "0 0 1 * * *")
    public void executeTask() {
        runOnStartup();

        logger.info("ScheduleCreatorJob executed at: " + LocalDateTime.now());
    }
}
