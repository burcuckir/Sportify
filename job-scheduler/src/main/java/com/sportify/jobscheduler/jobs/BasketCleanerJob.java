package com.sportify.jobscheduler.jobs;

import com.sportify.jobscheduler.client.reservationapi.ReservationApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class BasketCleanerJob {

    private final ReservationApiClient reservationApiClient;

    @Scheduled(cron = "0 0/1 * * * *")
    public void executeTask() {
        reservationApiClient.cleanBasket();
        System.out.println("BasketCleanerJob executed at: " + LocalDateTime.now());
    }
}

