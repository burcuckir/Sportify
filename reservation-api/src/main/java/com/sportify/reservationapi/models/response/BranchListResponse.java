package com.sportify.reservationapi.models.response;

import com.sportify.reservationapi.models.response.dto.BranchDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class BranchListResponse {

    private List<BranchDto> branchDtos;

}
