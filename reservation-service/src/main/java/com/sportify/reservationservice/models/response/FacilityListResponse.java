package com.sportify.reservationservice.models.response;

import com.sportify.reservationservice.models.response.dto.FacilityDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FacilityListResponse {

    List<FacilityDto> facilities;
}
