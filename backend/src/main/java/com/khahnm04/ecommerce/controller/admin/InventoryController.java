package com.khahnm04.ecommerce.controller.admin;

import com.khahnm04.ecommerce.dto.request.inventory.InventoryRequest;
import com.khahnm04.ecommerce.dto.response.ApiResponse;
import com.khahnm04.ecommerce.dto.response.inventory.InventoryResponse;
import com.khahnm04.ecommerce.service.inventory.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/admin/inventories")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    // API cập nhật kho (Set stock)
    @PostMapping("/update")
    public ApiResponse<InventoryResponse> updateInventory(
            @Valid @RequestBody InventoryRequest request
    ) {
        return ApiResponse.<InventoryResponse>builder()
                .data(inventoryService.updateInventory(request))
                .message("Inventory updated successfully")
                .build();
    }

    // API xem chi tiết tồn kho của 1 biến thể (để Admin biết hàng đang nằm ở đâu)
    @GetMapping("/variant/{variantId}")
    public ApiResponse<List<InventoryResponse>> getInventoryByVariant(
            @PathVariable Long variantId
    ) {
        return ApiResponse.<List<InventoryResponse>>builder()
                .data(inventoryService.getInventoryByVariant(variantId))
                .build();
    }

}
