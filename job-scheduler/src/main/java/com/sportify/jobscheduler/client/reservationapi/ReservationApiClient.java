package com.sportify.jobscheduler.client.reservationapi;

import com.sportify.jobscheduler.client.reservationapi.models.AddScheduleRequest;
import org.sportify.clientprovider.BaseApiClient;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReservationApiClient extends BaseApiClient {
    private static final String URL = "http://localhost:8082";

    public ReservationApiClient() {
        super(new RestTemplate(), URL);
    }

    public void cleanBasket() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        try {
             delete("/basket", null, headers);
        } catch (Exception e) {
            //TODO: log ekle.
        }
    }

    public void addSchedule(AddScheduleRequest request){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        try {
           post("/schedule", request, headers, Void.class);
        } catch (Exception e) {
            //TODO: log ekle.
        }
    }
}
