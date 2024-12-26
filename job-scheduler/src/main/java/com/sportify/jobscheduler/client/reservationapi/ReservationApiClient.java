package com.sportify.jobscheduler.client.reservationapi;

import com.sportify.jobscheduler.client.reservationapi.models.AddScheduleRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sportify.clientprovider.BaseApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReservationApiClient extends BaseApiClient {

    private static final Logger logger = LoggerFactory.getLogger(ReservationApiClient.class);

    public ReservationApiClient(@Value("${reservation.base.url}") String baseUrl) {
        super(new RestTemplate(), baseUrl);
    }

    public void cleanBasket() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        try {
            delete("/basket", null, headers);
        } catch (Exception e) {
            logger.error("Endpoint: /basket\n" +
                    "Http Method: DELETE\n" +
                    "Error Message: " + e.getMessage());
        }
    }

    public void addSchedule(AddScheduleRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        try {
            post("/schedule", request, headers, Void.class);
        } catch (Exception e) {
            logger.error("Endpoint: /schedule\n" +
                    "Http Method: POST\n" +
                    "Error Message: " + e.getMessage());
        }
    }
}
