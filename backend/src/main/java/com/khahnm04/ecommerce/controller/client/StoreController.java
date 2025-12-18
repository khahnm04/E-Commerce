package com.khahnm04.ecommerce.controller.client;

import com.khahnm04.ecommerce.dto.response.ApiResponse;
import com.khahnm04.ecommerce.dto.response.inventory.BranchResponse;
import com.khahnm04.ecommerce.service.inventory.BranchService;
import com.khahnm04.ecommerce.service.inventory.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/stores") // Base path: /api/v1/stores
@RequiredArgsConstructor
public class StoreController {

    private final BranchService branchService;
    private final InventoryService inventoryService; // 2. Inject thêm InventoryService

    // API 1: Lấy danh sách cửa hàng (để hiển thị bản đồ/địa chỉ)
    // URL: GET /api/v1/stores?city=...
    @GetMapping
    public ApiResponse<List<BranchResponse>> getStores(
            @RequestParam(required = false) String city
    ) {
        return ApiResponse.<List<BranchResponse>>builder()
                .data(branchService.getAllActiveBranches(city))
                .message("Get stores successfully")
                .build();
    }

    // API 2: Xem tổng tồn kho của 1 biến thể (để hiển thị "Còn hàng/Hết hàng")
    // URL: GET /api/v1/stores/stock/{variantId}
    @GetMapping("/stock/{variantId}")
    public ApiResponse<Long> getTotalStock(@PathVariable Long variantId) {
        return ApiResponse.<Long>builder()
                .data(inventoryService.getTotalStock(variantId))
                .build();
    }

}
