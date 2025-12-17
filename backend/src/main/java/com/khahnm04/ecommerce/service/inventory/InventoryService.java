package com.khahnm04.ecommerce.service.inventory;

import com.khahnm04.ecommerce.dto.request.inventory.InventoryRequest;
import com.khahnm04.ecommerce.dto.response.inventory.InventoryResponse;

import java.util.List;

public interface InventoryService {

    InventoryResponse updateInventory(InventoryRequest request);

    List<InventoryResponse> getInventoryByVariant(Long variantId);

    Long getTotalStock(Long variantId);

}
