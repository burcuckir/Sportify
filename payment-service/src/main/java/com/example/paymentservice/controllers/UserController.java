package com.example.paymentservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/basket")
public class UserController {

    @GetMapping()
    public String getBasketItems() {
      return "Merhaba";
    }
}
