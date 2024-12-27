package com.sportify.reservationapi.controllers;

import com.sportify.reservationapi.models.response.BranchListResponse;
import com.sportify.reservationapi.services.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/branch")
public class BranchController {

    private final BranchService branchService;

    @GetMapping("")
    public ResponseEntity<BranchListResponse> getAllBranch() {
        BranchListResponse branchListResponse = branchService.getAllBranch();
        if (branchListResponse != null) {
            return ResponseEntity.ok(branchListResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
