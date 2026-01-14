package com.khahnm04.ecommerce.modules.inventory.service.impl;

import com.khahnm04.ecommerce.shared.dto.PageResponse;
import com.khahnm04.ecommerce.modules.inventory.dto.response.InventoryResponse;
import com.khahnm04.ecommerce.modules.inventory.entity.Inventory;
import com.khahnm04.ecommerce.core.exception.AppException;
import com.khahnm04.ecommerce.core.exception.ErrorCode;
import com.khahnm04.ecommerce.modules.inventory.mapper.InventoryMapper;
import com.khahnm04.ecommerce.modules.inventory.repository.InventoryRepository;
import com.khahnm04.ecommerce.modules.product.repository.ProductVariantRepository;
import com.khahnm04.ecommerce.modules.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductVariantRepository variantRepository;
    private final InventoryMapper inventoryMapper;

    /**
     * Xem danh sách các kho đang giữ biến thể sản phẩm này.
     * Thường dùng cho Admin để biết hàng đang nằm rải rác ở đâu.
     */
    @Override
    public List<InventoryResponse> getInventoriesByVariant(Long variantId) {
        if (!variantRepository.existsById(variantId)) {
            throw new AppException(ErrorCode.PRODUCT_VARIANT_NOT_FOUND);
        }
        // Lấy danh sách inventory của variant này ở tất cả các chi nhánh
        List<Inventory> inventories = inventoryRepository.findByProductVariantId(variantId);
        return inventoryMapper.toInventoryResponseList(inventories);
    }

    /**
     * API quản lý kho trung tâm: Hỗ trợ tìm kiếm, lọc theo chi nhánh và lọc hàng sắp hết.
     */
    @Override
    public PageResponse<InventoryResponse> getInventories(Long branchId, String keyword, Boolean isLowStock, int page, int size) {
        // Sắp xếp theo ngày cập nhật mới nhất để dễ theo dõi biến động
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("updatedAt").descending());

        // Logic lọc hàng sắp hết: Nếu isLowStock = true thì lấy ngưỡng <= 10, ngược lại thì null (không lọc)
        // Bạn có thể cấu hình số 10 này trong application.yml nếu muốn linh động hơn
        Long threshold = (isLowStock != null && isLowStock) ? 10L : null;

        // 1. Gọi Repository để lấy Page<Entity>
        Page<Inventory> pageData = inventoryRepository.findWithFilters(branchId, keyword, threshold, pageable);

        // 2. Map sang Page<DTO> bằng method reference (gọn gàng hơn)
        Page<InventoryResponse> dtoPage = pageData.map(inventoryMapper::toInventoryResponse);

        // 3. Đóng gói kết quả trả về
        return PageResponse.fromPage(dtoPage);
    }

    /**
     * Xem chi tiết một bản ghi tồn kho cụ thể.
     */
    @Override
    public InventoryResponse getInventoryById(Long id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.INVENTORY_NOT_FOUND));
        return inventoryMapper.toInventoryResponse(inventory);
    }

    /**
     * Lấy tổng số lượng tồn kho của 1 biến thể trên toàn hệ thống.
     * Dùng cho phía Client (Người mua hàng) để hiện chữ "Còn hàng/Hết hàng".
     */
    @Override
    public Long getTotalStock(Long variantId) {
        return inventoryRepository.sumQuantityByVariantId(variantId);
    }

}
