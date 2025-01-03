package com.sportify.reservationapi.services;

import com.sportify.reservationapi.entities.Branch;
import com.sportify.reservationapi.mappers.BranchMapper;
import com.sportify.reservationapi.models.response.BranchListResponse;
import com.sportify.reservationapi.repositories.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;

    public BranchListResponse getAllBranch() {
        List<Branch> branches = branchRepository.getBranchesByIsActive(true);
        return BranchMapper.mapToBranchListResponse(branches);
    }
}
