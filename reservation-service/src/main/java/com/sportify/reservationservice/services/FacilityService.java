package com.sportify.reservationservice.services;

import com.sportify.reservationservice.entities.Facility;
import com.sportify.reservationservice.mappers.FacilityMapper;
import com.sportify.reservationservice.models.response.FacilityListResponse;
import com.sportify.reservationservice.repositories.FacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FacilityService {

    @Autowired
    private FacilityRepository facilityRepository;

    public FacilityListResponse getFacilityByBranch(UUID branchId) {
        List<Facility> facilities = facilityRepository.findAllByBranchIdAndIsActive(branchId,true);
        return FacilityMapper.mapToFacilityResponse(facilities);
    }
}
