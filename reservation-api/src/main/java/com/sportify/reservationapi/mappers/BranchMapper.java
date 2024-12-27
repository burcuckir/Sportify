package com.sportify.reservationapi.mappers;

import com.sportify.reservationapi.entities.Branch;
import com.sportify.reservationapi.models.response.BranchListResponse;
import com.sportify.reservationapi.models.response.dto.BranchDto;

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
