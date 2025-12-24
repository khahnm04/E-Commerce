package com.khahnm04.ecommerce.modules.inventory.service;

import com.khahnm04.ecommerce.shared.enums.BranchStatus;
import com.khahnm04.ecommerce.modules.inventory.dto.request.BranchRequest;
import com.khahnm04.ecommerce.shared.dto.PageResponse;
import com.khahnm04.ecommerce.modules.inventory.dto.response.BranchResponse;
import java.util.List;

public interface BranchService {

    // Admin
    BranchResponse createBranch(BranchRequest request);
    BranchResponse updateBranch(Long id, BranchRequest request);
    void softDeleteBranch(Long id);
    BranchResponse getBranchDetailById(Long id);
    PageResponse<BranchResponse> getBranchesAdmin(int page, int size, String sort, String keyword, BranchStatus status);

    // Client
    List<BranchResponse> getAllActiveBranches(String city); // city có thể null

}
