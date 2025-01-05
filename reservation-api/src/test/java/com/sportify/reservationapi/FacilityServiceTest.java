package com.sportify.reservationapi;

import com.sportify.reservationapi.entities.Facility;
import com.sportify.reservationapi.models.response.FacilityListResponse;
import com.sportify.reservationapi.repositories.FacilityRepository;
import com.sportify.reservationapi.services.facility.FacilityServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FacilityServiceTest {

    @Mock
    private FacilityRepository facilityRepository;

    @InjectMocks
    private FacilityServiceImpl facilityService;

    @Test
    void getFacilityByBranch_shouldReturnFacilities_whenFacilitiesExist() {
        UUID branchId = UUID.randomUUID();
        List<Facility> facilities = new ArrayList<>();
        Facility facility = new Facility();
        facility.setId(UUID.randomUUID());
        facility.setName("Saloon1");
        facilities.add(facility);

        when(facilityRepository.findAllByBranchIdAndIsActive(branchId, true)).thenReturn(facilities);

        FacilityListResponse response = facilityService.getFacilityByBranch(branchId);

        assertNotNull(response);
        verify(facilityRepository, times(1)).findAllByBranchIdAndIsActive(branchId, true);
    }

    @Test
    void getFacilityByBranch_shouldReturnEmptyResponse_whenNoFacilitiesExist() {
        UUID branchId = UUID.randomUUID();
        when(facilityRepository.findAllByBranchIdAndIsActive(branchId, true)).thenReturn(List.of());

        FacilityListResponse response = facilityService.getFacilityByBranch(branchId);

        assertNotNull(response);
        verify(facilityRepository, times(1)).findAllByBranchIdAndIsActive(branchId, true);
    }
}
