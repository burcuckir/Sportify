package com.sportify.reservationapi.repositories;

import com.sportify.reservationapi.entities.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BranchRepository extends JpaRepository<Branch, Long> {

    List<Branch> getBranchesByIsActive(Boolean isActive);
}
