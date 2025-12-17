package com.khahnm04.ecommerce.service.inventory.impl;

import com.khahnm04.ecommerce.dto.request.inventory.InventoryRequest;
import com.khahnm04.ecommerce.dto.response.inventory.InventoryResponse;
import com.khahnm04.ecommerce.entity.inventory.Branch;
import com.khahnm04.ecommerce.entity.inventory.Inventory;
import com.khahnm04.ecommerce.entity.product.ProductVariant;
import com.khahnm04.ecommerce.exception.AppException;
import com.khahnm04.ecommerce.exception.ErrorCode;
import com.khahnm04.ecommerce.repository.BranchRepository;
import com.khahnm04.ecommerce.repository.InventoryRepository;
import com.khahnm04.ecommerce.repository.ProductVariantRepository;
import com.khahnm04.ecommerce.service.inventory.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductVariantRepository variantRepository;
    private final BranchRepository branchRepository;

    @Override
    @Transactional
    public InventoryResponse updateInventory(InventoryRequest request) {
        // 1. Validate Variant & Branch
        ProductVariant variant = variantRepository.findById(request.getProductVariantId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIANT_NOT_FOUND)); // Cần thêm mã lỗi này

        Branch branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() -> new AppException(ErrorCode.BRANCH_NOT_FOUND));

        // 2. Tìm hoặc Tạo mới record Inventory
        Inventory inventory = inventoryRepository.findByProductVariantIdAndBranchId(variant.getId(), branch.getId())
                .orElse(Inventory.builder()
                        .productVariant(variant)
                        .branch(branch)
                        .quantity(0L)
                        .build());

        // 3. Update số lượng (Ở đây là set cứng, thực tế có thể là cộng dồn từ nhập kho)
        inventory.setQuantity(request.getQuantity());

        // Lưu và trả về
        Inventory saved = inventoryRepository.save(inventory);
        return toResponse(saved);
    }

    @Override
    public List<InventoryResponse> getInventoryByVariant(Long variantId) {
        return inventoryRepository.findByProductVariantId(variantId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Long getTotalStock(Long variantId) {
        return inventoryRepository.sumQuantityByVariantId(variantId);
    }

    private InventoryResponse toResponse(Inventory inventory) {
        return InventoryResponse.builder()
                .id(inventory.getId())
                .productVariantId(inventory.getProductVariant().getId())
                // .variantName(...) // Nếu cần tên biến thể thì lấy từ entity
                .branchId(inventory.getBranch().getId())
                .branchName(inventory.getBranch().getName())
                .quantity(inventory.getQuantity())
                .build();
    }

}
