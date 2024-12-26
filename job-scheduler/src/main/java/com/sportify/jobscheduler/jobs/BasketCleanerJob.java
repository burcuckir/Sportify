package com.sportify.jobscheduler.jobs;

import com.sportify.jobscheduler.client.reservationapi.ReservationApiClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class BasketCleanerJob {

    private final ReservationApiClient reservationApiClient;
    private static final Logger logger = LoggerFactory.getLogger(BasketCleanerJob.class);

    @Scheduled(cron = "0 0/1 * * * *")
    public void executeTask() {
        reservationApiClient.cleanBasket();

       logger.info("BasketCleanerJob executed at: " + LocalDateTime.now());
    }
}

