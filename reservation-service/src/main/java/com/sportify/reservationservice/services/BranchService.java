package com.sportify.reservationservice.services;

import com.sportify.reservationservice.entities.Branch;
import com.sportify.reservationservice.mappers.BranchMapper;
import com.sportify.reservationservice.models.response.BranchListResponse;
import com.sportify.reservationservice.repositories.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchService {

    @Autowired
    private BranchRepository branchRepository;

    public BranchListResponse getAllBranch() {
        List<Branch> branches = branchRepository.getBranchesByIsActive(true);
        return BranchMapper.mapToBranchListResponse(branches);
    }
}
