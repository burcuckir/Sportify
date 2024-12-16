package com.sportify.reservationservice.mappers;

import com.sportify.reservationservice.entities.Branch;
import com.sportify.reservationservice.models.response.BranchListResponse;
import com.sportify.reservationservice.models.response.dto.BranchDto;

import java.util.ArrayList;
import java.util.List;

public class BranchMapper {

    public static BranchListResponse mapToBranchListResponse(List<Branch> branches) {

        BranchListResponse branchListResponse = new BranchListResponse();
        List<BranchDto> branchDtoList = new ArrayList<>();

        branches.forEach(branch -> {
            BranchDto branchDto = new BranchDto();
            branchDto.setId(branch.getId());
            branchDto.setName(branch.getName());
            branchDtoList.add(branchDto);
        });

        branchListResponse.setBranchDtos(branchDtoList);

        return branchListResponse;
    }
}
