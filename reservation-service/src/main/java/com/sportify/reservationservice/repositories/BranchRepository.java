package com.sportify.reservationservice.repositories;

import com.sportify.reservationservice.entities.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BranchRepository extends JpaRepository<Branch, Long> {

    List<Branch> getBranchesByIsActive(Boolean isActive);
}
