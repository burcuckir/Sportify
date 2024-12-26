package com.sportify.paymentservice.client.reservationapi;

import org.sportify.clientprovider.BaseApiClient;
import com.sportify.paymentservice.client.reservationapi.models.BasketResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReservationApiClient extends BaseApiClient {

    @Value("${reservation.api.base.url}")
    private String url;

    public ReservationApiClient() {
        super(new RestTemplate(), "http://localhost:8082");
    }

    public BasketResponse getBasket(String userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("x-user-id", userId);
        try {
            return get("/basket", null, headers, BasketResponse.class).getBody();
        } catch (Exception e) {
            //TODO:LOG EKLE
            return null;
        }
    }
}
