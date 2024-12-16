package com.sportify.reservationservice.repositories;

import com.sportify.reservationservice.entities.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface FacilityRepository extends JpaRepository<Facility, Long> {
    List<Facility> findAllByBranchIdAndIsActive(UUID id, Boolean isActive);

    Facility findById(UUID id);
}