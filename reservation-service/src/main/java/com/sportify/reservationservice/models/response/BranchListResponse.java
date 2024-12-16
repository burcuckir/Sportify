package com.sportify.reservationservice.models.response;

import com.sportify.reservationservice.models.response.dto.BranchDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class BranchListResponse {

    private List<BranchDto> branchDtos;

}
