package com.khahnm04.ecommerce.modules.inventory.service;

import com.khahnm04.ecommerce.shared.enums.StockMovementType;
import com.khahnm04.ecommerce.modules.inventory.dto.request.StockMovementRequest;
import com.khahnm04.ecommerce.shared.dto.PageResponse;
import com.khahnm04.ecommerce.modules.inventory.dto.response.StockMovementResponse;

public interface StockMovementService {

    void inbound(StockMovementRequest request);

    void outbound(StockMovementRequest request);

    void adjustment(StockMovementRequest request);

    PageResponse<StockMovementResponse> getAllMovements(
            Long inventoryId, Long branchId, Long productId, Long productVariantId, StockMovementType type, int page, int size
    );

}

