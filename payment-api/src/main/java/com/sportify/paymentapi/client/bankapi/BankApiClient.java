package com.sportify.paymentapi.client.bankapi;

import com.sportify.paymentapi.client.bankapi.models.BankPayRequest;
import com.sportify.paymentapi.client.bankapi.models.BankPayResponse;
import org.sportify.clientprovider.BaseApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BankApiClient extends BaseApiClient {

    public BankApiClient(@Value("${mock.server.base.url}") String baseUrl) {
        super(new RestTemplate(), baseUrl);
    }

    public BankPayResponse pay(BankPayRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        return post("/api/payment/pay", request, headers, BankPayResponse.class).getBody();
    }
}
