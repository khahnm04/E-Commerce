package com.khahnm04.ecommerce.modules.inventory.service;

import com.khahnm04.ecommerce.shared.dto.PageResponse;
import com.khahnm04.ecommerce.modules.inventory.dto.response.InventoryResponse;

import java.util.List;

public interface InventoryService {

    // Xem chi tiết tồn kho của 1 biến thể tại các chi nhánh (Dùng cho Admin ProductVariantController)
    List<InventoryResponse> getInventoriesByVariant(Long variantId);

    // Xem danh sách tồn kho, lọc, tìm kiếm, cảnh báo hết hàng (Dùng cho Admin InventoryController)
    PageResponse<InventoryResponse> getInventories(Long branchId, String keyword, Boolean isLowStock, int page, int size);

    // Xem chi tiết 1 record inventory
    InventoryResponse getInventoryById(Long id);

    // Lấy tổng tồn kho của 1 biến thể (Cộng dồn tất cả chi nhánh) - Dùng cho Client hiển thị
    Long getTotalStock(Long variantId);

}
