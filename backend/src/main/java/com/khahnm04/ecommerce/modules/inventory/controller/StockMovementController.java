package com.khahnm04.ecommerce.modules.inventory.controller;

import com.khahnm04.ecommerce.shared.enums.StockMovementType;
import com.khahnm04.ecommerce.modules.inventory.dto.request.StockMovementRequest;
import com.khahnm04.ecommerce.shared.dto.ApiResponse;
import com.khahnm04.ecommerce.shared.dto.PageResponse;
import com.khahnm04.ecommerce.modules.inventory.dto.response.StockMovementResponse;
import com.khahnm04.ecommerce.modules.inventory.service.StockMovementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/stock-movements")
@RequiredArgsConstructor
public class StockMovementController {

    private final StockMovementService stockMovementService;

    @PostMapping("/inbound")
    public ApiResponse<Void> inbound(
            @Valid @RequestBody StockMovementRequest request
    ) {
        stockMovementService.inbound(request);
        return ApiResponse.<Void>builder()
                .message("warehouse receipt created successfully")
                .build();
    }

    @PostMapping("/outbound")
    public ApiResponse<Void> outbound(
            @Valid @RequestBody StockMovementRequest request
    ) {
        stockMovementService.outbound(request);
        return ApiResponse.<Void>builder()
                .message("warehouse release form successfully created.")
                .build();
    }

    @PostMapping("/adjustment")
    public ApiResponse<Void> adjustment(@RequestBody @Valid StockMovementRequest request) {
        stockMovementService.adjustment(request);
        return ApiResponse.<Void>builder()
                .message("inventory balance successfully achieved.")
                .build();
    }

    @GetMapping
    public ApiResponse<List<StockMovementResponse>> getHistory(
            @RequestParam(required = false) Long inventoryId,
            @RequestParam(required = false) Long branchId,
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) Long productVariantId,
            @RequestParam(required = false) StockMovementType type,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageResponse<StockMovementResponse> pageResponse =
                stockMovementService.getAllMovements(inventoryId, branchId, productId, productVariantId, type, page, size);
        return ApiResponse.<List<StockMovementResponse>>builder()
                .data(pageResponse.getData())
                .meta(pageResponse.getMeta())
                .message("historical data retrieved successfully.")
                .build();
    }

}
