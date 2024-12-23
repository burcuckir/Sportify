package com.sportify.reservationservice.services;

import com.sportify.reservationservice.entities.Facility;
import com.sportify.reservationservice.mappers.FacilityMapper;
import com.sportify.reservationservice.models.response.FacilityListResponse;
import com.sportify.reservationservice.repositories.FacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FacilityService {

    private final FacilityRepository facilityRepository;

    public FacilityListResponse getFacilityByBranch(UUID branchId) {
        List<Facility> facilities = facilityRepository.findAllByBranchIdAndIsActive(branchId,true);
        return FacilityMapper.mapToFacilityResponse(facilities);
    }
}
