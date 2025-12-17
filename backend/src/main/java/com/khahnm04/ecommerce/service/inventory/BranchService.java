package com.khahnm04.ecommerce.service.inventory;

import com.khahnm04.ecommerce.common.enums.BranchStatus;
import com.khahnm04.ecommerce.dto.request.inventory.BranchRequest;
import com.khahnm04.ecommerce.dto.response.PageResponse;
import com.khahnm04.ecommerce.dto.response.inventory.BranchResponse;
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
