package com.sportify.paymentservice.controllers;

import com.sportify.paymentservice.models.requests.PayRequest;
import com.sportify.paymentservice.models.response.PayResponse;
import com.sportify.paymentservice.services.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("pay")
    public ResponseEntity<PayResponse> pay(@RequestHeader("x-user-id") String userIdHeader, @Valid @RequestBody PayRequest request) {
        if (!userIdHeader.isEmpty()) {
            PayResponse response = paymentService.pay(userIdHeader ,request);
            return  ResponseEntity.ok(response);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}