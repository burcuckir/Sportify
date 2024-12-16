package com.sportify.reservationservice.controllers;

import com.sportify.reservationservice.models.request.AddScheduleRequest;
import com.sportify.reservationservice.models.response.ScheduleListResponse;
import com.sportify.reservationservice.services.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/facility/{id}")
    public ResponseEntity<ScheduleListResponse> getScheduleByFacilityId(@PathVariable UUID id) {
        ScheduleListResponse scheduleListResponse = scheduleService.getScheduleByFacility(id);
        if (scheduleListResponse != null) {
            return ResponseEntity.ok(scheduleListResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("")
    public ResponseEntity<Void> addSchedule(@Valid @RequestBody AddScheduleRequest request) {
        scheduleService.addSchedule(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
