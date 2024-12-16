package com.sportify.reservationservice.mappers;

import com.sportify.reservationservice.entities.Facility;
import com.sportify.reservationservice.models.response.FacilityListResponse;
import com.sportify.reservationservice.models.response.dto.FacilityDto;

import java.util.ArrayList;
import java.util.List;

public class FacilityMapper {

    public static FacilityListResponse mapToFacilityResponse(List<Facility> facilities) {
        FacilityListResponse facilityListResponse = new FacilityListResponse();
        List<FacilityDto> facilityDtoList = new ArrayList<>();
        facilities.forEach(f -> {
            FacilityDto facilityDto = new FacilityDto();
            facilityDto.setId(f.getId());
            facilityDto.setName(f.getName());
            facilityDtoList.add(facilityDto);
        });
        facilityListResponse.setFacilities(facilityDtoList);
        return facilityListResponse;
    }
}
