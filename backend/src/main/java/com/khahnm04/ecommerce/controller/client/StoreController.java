package com.khahnm04.ecommerce.controller.client;

import com.khahnm04.ecommerce.dto.response.ApiResponse;
import com.khahnm04.ecommerce.dto.response.inventory.BranchResponse;
import com.khahnm04.ecommerce.service.inventory.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/stores")
@RequiredArgsConstructor
public class StoreController {

    private final BranchService branchService;

    @GetMapping
    public ApiResponse<List<BranchResponse>> getStores(
            @RequestParam(required = false) String city
    ) {
        return ApiResponse.<List<BranchResponse>>builder()
                .data(branchService.getAllActiveBranches(city))
                .message("Get stores successfully")
                .build();
    }

}
