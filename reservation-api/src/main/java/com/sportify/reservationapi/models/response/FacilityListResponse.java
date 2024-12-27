package com.sportify.reservationapi.models.response;

import com.sportify.reservationapi.models.response.dto.FacilityDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FacilityListResponse {

    List<FacilityDto> facilities;
}
