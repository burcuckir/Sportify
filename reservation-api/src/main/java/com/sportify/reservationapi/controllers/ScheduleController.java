package com.sportify.reservationapi.controllers;

import com.sportify.reservationapi.models.request.AddScheduleRequest;
import com.sportify.reservationapi.models.response.ScheduleListResponse;
import com.sportify.reservationapi.services.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

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
