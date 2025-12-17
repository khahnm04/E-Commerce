package com.khahnm04.ecommerce.controller.admin;

import com.khahnm04.ecommerce.common.enums.BranchStatus;
import com.khahnm04.ecommerce.dto.request.inventory.BranchRequest;
import com.khahnm04.ecommerce.dto.response.ApiResponse;
import com.khahnm04.ecommerce.dto.response.PageResponse;
import com.khahnm04.ecommerce.dto.response.inventory.BranchResponse;
import com.khahnm04.ecommerce.service.inventory.BranchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/admin/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @PostMapping
    public ApiResponse<BranchResponse> createBranch(
            @Valid @RequestBody BranchRequest request
    ) {
        return ApiResponse.<BranchResponse>builder()
                .data(branchService.createBranch(request))
                .message("Branch created successfully")
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<BranchResponse> updateBranch(
            @PathVariable Long id,
            @Valid @RequestBody BranchRequest request
    ) {
        return ApiResponse.<BranchResponse>builder()
                .data(branchService.updateBranch(id, request))
                .message("Branch updated successfully")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<BranchResponse> getBranchById(@PathVariable Long id) {
        return ApiResponse.<BranchResponse>builder()
                .data(branchService.getBranchDetailById(id))
                .build();
    }

    @GetMapping
    public ApiResponse<PageResponse<BranchResponse>> getBranches(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) BranchStatus status
    ) {
        return ApiResponse.<PageResponse<BranchResponse>>builder()
                .data(branchService.getBranchesAdmin(page, size, sort, keyword, status))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteBranch(@PathVariable Long id) {
        branchService.softDeleteBranch(id);
        return ApiResponse.<Void>builder()
                .message("Branch deleted successfully")
                .build();
    }

}
