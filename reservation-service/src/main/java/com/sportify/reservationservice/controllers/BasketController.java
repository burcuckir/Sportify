package com.sportify.reservationservice.controllers;

import com.sportify.reservationservice.models.request.AddToBasketRequest;
import com.sportify.reservationservice.models.response.BasketListResponse;
import com.sportify.reservationservice.services.BasketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/basket")
public class BasketController  {

    @Autowired
    private BasketService basketService;

    @GetMapping()
    public ResponseEntity<BasketListResponse> getBasketItems(@RequestHeader("x-user-id") String userIdHeader) {
        BasketListResponse basketListResponse = basketService.getBasketByUserId(UUID.fromString(userIdHeader));
        if (basketListResponse != null) {
            return ResponseEntity.ok(basketListResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("")
    public ResponseEntity<Void> addToBasket(@RequestHeader("x-user-id") String userIdHeader, @Valid @RequestBody AddToBasketRequest request) {
        if (userIdHeader != null) {
            basketService.addToBasket(UUID.fromString(userIdHeader), request.getScheduleId());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> removeBasketItem(@RequestHeader("x-user-id") String userIdHeader, @PathVariable UUID id) {
        if (userIdHeader != null) {
            basketService.removeBasketItem(UUID.fromString(userIdHeader), id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping()
    public ResponseEntity<Void> cleanBasket() {
        basketService.cleanBasket();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}