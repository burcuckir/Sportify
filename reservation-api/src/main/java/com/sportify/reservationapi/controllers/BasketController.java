package com.sportify.reservationapi.controllers;

import com.sportify.reservationapi.models.request.AddToBasketRequest;
import com.sportify.reservationapi.models.response.BasketListResponse;
import com.sportify.reservationapi.services.basket.BasketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/basket")
public class BasketController  {

    private final BasketService basketService;

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