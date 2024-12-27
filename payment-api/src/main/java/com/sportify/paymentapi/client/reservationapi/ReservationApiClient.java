package com.sportify.paymentapi.client.reservationapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sportify.clientprovider.BaseApiClient;
import com.sportify.paymentapi.client.reservationapi.models.BasketResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReservationApiClient extends BaseApiClient {

    public ReservationApiClient(@Value("${reservation.api.base.url}") String baseUrl) {
        super(new RestTemplate(), baseUrl);
    }

    private static final Logger logger = LoggerFactory.getLogger(ReservationApiClient.class);

    public BasketResponse getBasket(String userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("x-user-id", userId);
        try {
            return get("/basket", null, headers, BasketResponse.class).getBody();
        } catch (Exception e) {
            logger.error("Endpoint: /basket\n" +
                    "Http Method: GET\n" +
                    "Error Message: " + e.getMessage());
            return null;
        }
    }
}
