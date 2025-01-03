package com.sportify.reservationapi.services;

import com.sportify.reservationapi.models.response.FacilityListResponse;

import java.util.UUID;

public interface FacilityService {
    FacilityListResponse getFacilityByBranch(UUID branchId);
}
