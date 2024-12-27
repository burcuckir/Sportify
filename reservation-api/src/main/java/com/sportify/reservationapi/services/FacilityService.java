package com.sportify.reservationapi.services;

import com.sportify.reservationapi.entities.Facility;
import com.sportify.reservationapi.mappers.FacilityMapper;
import com.sportify.reservationapi.models.response.FacilityListResponse;
import com.sportify.reservationapi.repositories.FacilityRepository;
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
