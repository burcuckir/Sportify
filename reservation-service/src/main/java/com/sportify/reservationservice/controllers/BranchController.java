package com.sportify.reservationservice.controllers;

import com.sportify.reservationservice.models.response.BranchListResponse;
import com.sportify.reservationservice.services.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/branch")
public class BranchController {

    @Autowired
    private BranchService branchService;

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
