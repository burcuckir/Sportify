package com.sportify.paymentservice.client.bankapi;

import com.sportify.paymentservice.client.BaseApiClient;
import com.sportify.paymentservice.client.bankapi.models.BankPayRequest;
import com.sportify.paymentservice.client.bankapi.models.BankPayResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BankApiClient extends BaseApiClient {

    @Value("${wiremock.base.url}")
    private static String url;

    public BankApiClient() {
        super(new RestTemplate(), "http://localhost:8083"); //TODO:DÜZELT
    }

    public BankPayResponse pay(BankPayRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        return post("/api/payment/pay", request, headers, BankPayResponse.class).getBody();
    }
}
