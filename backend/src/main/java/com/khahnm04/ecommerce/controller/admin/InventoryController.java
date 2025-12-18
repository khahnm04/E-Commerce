package com.khahnm04.ecommerce.controller.admin;

import com.khahnm04.ecommerce.dto.response.ApiResponse;
import com.khahnm04.ecommerce.dto.response.PageResponse;
import com.khahnm04.ecommerce.dto.response.inventory.InventoryResponse;
import com.khahnm04.ecommerce.service.inventory.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/admin/inventories")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public ApiResponse<List<InventoryResponse>> getInventories(
            @RequestParam(required = false) Long branchId,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Boolean isLowStock,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageResponse<InventoryResponse> pageResponse =
                inventoryService.getInventories(branchId, keyword, isLowStock, page, size);

        return ApiResponse.<List<InventoryResponse>>builder()
                .data(pageResponse.getData())
                .meta(pageResponse.getMeta())
                .message("Inventory list retrieved successfully")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<InventoryResponse> getInventoryById(@PathVariable Long id) {
        return ApiResponse.<InventoryResponse>builder()
                .data(inventoryService.getInventoryById(id))
                .message("Inventory details retrieved successfully")
                .build();
    }

}
