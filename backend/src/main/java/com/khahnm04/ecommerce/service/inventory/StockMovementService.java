package com.khahnm04.ecommerce.service.inventory;

import com.khahnm04.ecommerce.common.enums.StockMovementType;
import com.khahnm04.ecommerce.dto.request.inventory.StockMovementRequest;
import com.khahnm04.ecommerce.dto.response.PageResponse;
import com.khahnm04.ecommerce.dto.response.inventory.StockMovementResponse;

public interface StockMovementService {

    void inbound(StockMovementRequest request);

    void outbound(StockMovementRequest request);

    void adjustment(StockMovementRequest request);

    PageResponse<StockMovementResponse> getAllMovements(
            Long inventoryId, Long branchId, Long productId, Long productVariantId, StockMovementType type, int page, int size
    );

}

