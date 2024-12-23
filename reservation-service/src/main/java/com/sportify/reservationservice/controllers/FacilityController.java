package com.sportify.reservationservice.controllers;

import com.sportify.reservationservice.models.response.FacilityListResponse;
import com.sportify.reservationservice.services.FacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/facility")
public class FacilityController {

    private final FacilityService facilityService;

    @GetMapping("/{id}")
    public ResponseEntity<FacilityListResponse> getFacilityById(@PathVariable UUID id) {
        FacilityListResponse facilityListResponse = facilityService.getFacilityByBranch(id);
        if (facilityListResponse != null) {
            return ResponseEntity.ok(facilityListResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
