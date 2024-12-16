package com.sportify.reservationservice.controllers;

import com.sportify.reservationservice.models.response.OrderListResponse;
import com.sportify.reservationservice.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("")
    public ResponseEntity<OrderListResponse> getOrders(@RequestHeader("x-user-id") String userIdHeader) {
        OrderListResponse orderListResponse = orderService.getOrders(UUID.fromString(userIdHeader));
        if (orderListResponse != null) {
            return ResponseEntity.ok(orderListResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
