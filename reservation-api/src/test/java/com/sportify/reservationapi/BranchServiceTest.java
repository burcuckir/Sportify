package com.sportify.reservationapi;
import com.sportify.reservationapi.entities.Branch;
import com.sportify.reservationapi.models.response.BranchListResponse;
import com.sportify.reservationapi.repositories.BranchRepository;
import com.sportify.reservationapi.services.branch.BranchServiceImpl;
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
public class BranchServiceTest {
    @Mock
    private BranchRepository branchRepository;

    @InjectMocks
    private BranchServiceImpl branchService;

    @Test
    void getAllBranch_shouldReturnBranchList_whenActiveBranchesExist() {
        List<Branch> branches = new ArrayList<>();
        Branch branch = new Branch();
        branch.setId(UUID.randomUUID());
        branch.setName("Tennis");
        branch.setIsActive(true);
        branches.add(branch);

        when(branchRepository.getBranchesByIsActive(true)).thenReturn(branches);

        BranchListResponse response = branchService.getAllBranch();

        assertNotNull(response);
        verify(branchRepository, times(1)).getBranchesByIsActive(true);
    }

    @Test
    void getAllBranch_shouldReturnEmptyResponse_whenNoActiveBranches() {
        when(branchRepository.getBranchesByIsActive(true)).thenReturn(List.of());

        BranchListResponse response = branchService.getAllBranch();

        assertNotNull(response);
        verify(branchRepository, times(1)).getBranchesByIsActive(true);
    }
}
