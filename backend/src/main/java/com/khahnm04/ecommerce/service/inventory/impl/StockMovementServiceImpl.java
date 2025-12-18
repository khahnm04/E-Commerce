package com.khahnm04.ecommerce.service.inventory.impl;

import com.khahnm04.ecommerce.common.enums.StockMovementType;
import com.khahnm04.ecommerce.dto.request.inventory.StockMovementRequest;
import com.khahnm04.ecommerce.dto.response.PageResponse;
import com.khahnm04.ecommerce.dto.response.inventory.StockMovementResponse;
import com.khahnm04.ecommerce.entity.inventory.Branch;
import com.khahnm04.ecommerce.entity.inventory.Inventory;
import com.khahnm04.ecommerce.entity.inventory.StockMovement;
import com.khahnm04.ecommerce.entity.product.ProductVariant;
import com.khahnm04.ecommerce.exception.AppException;
import com.khahnm04.ecommerce.exception.ErrorCode;
import com.khahnm04.ecommerce.mapper.StockMovementMapper;
import com.khahnm04.ecommerce.repository.BranchRepository;
import com.khahnm04.ecommerce.repository.InventoryRepository;
import com.khahnm04.ecommerce.repository.ProductVariantRepository;
import com.khahnm04.ecommerce.repository.StockMovementRepository;
import com.khahnm04.ecommerce.service.inventory.StockMovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockMovementServiceImpl implements StockMovementService {

    private final StockMovementRepository stockMovementRepository;
    private final InventoryRepository inventoryRepository;
    private final ProductVariantRepository productVariantRepository;
    private final BranchRepository branchRepository;
    private final StockMovementMapper stockMovementMapper;

    @Override
    @Transactional
    public void inbound(StockMovementRequest request) {
        Inventory inventory = getOrCreateInventory(request.getProductVariantId(), request.getBranchId());

        inventory.setQuantity(inventory.getQuantity() + request.getQuantity());
        inventoryRepository.save(inventory);

        createMovement(inventory, StockMovementType.IN, request.getQuantity(), request.getReferenceId(), request.getNote());
    }

    @Override
    @Transactional
    public void outbound(StockMovementRequest request) {
        Inventory inventory = inventoryRepository
                .findByProductVariantIdAndBranchId(request.getProductVariantId(), request.getBranchId())
                .orElseThrow(() -> new AppException(ErrorCode.INVENTORY_NOT_FOUND));

        if (inventory.getQuantity() < request.getQuantity()) {
            throw new AppException(ErrorCode.INSUFFICIENT_STOCK);
        }

        inventory.setQuantity(inventory.getQuantity() - request.getQuantity());
        inventoryRepository.save(inventory);

        createMovement(inventory, StockMovementType.OUT, request.getQuantity(), request.getReferenceId(), request.getNote());
    }

    @Override
    @Transactional
    public void adjustment(StockMovementRequest request) {
        Inventory inventory = getOrCreateInventory(request.getProductVariantId(), request.getBranchId());

        long currentQty = inventory.getQuantity();
        long actualQty = request.getQuantity();

        if (currentQty == actualQty) {
            return;
        }

        inventory.setQuantity(actualQty);
        inventoryRepository.save(inventory);

        long diff = Math.abs(actualQty - currentQty);
        String note = String.format("%s (System: %d -> Actual: %d)",
                request.getNote() != null ? request.getNote() : "Inventory Check",
                currentQty, actualQty);

        createMovement(inventory, StockMovementType.ADJUSTMENT, diff, request.getReferenceId(), note);
    }

    @Override
    public PageResponse<StockMovementResponse> getAllMovements(
            Long inventoryId, Long branchId, Long productId, Long productVariantId, StockMovementType type, int page, int size
    ) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());
        Page<StockMovement> pageData = stockMovementRepository.findWithFilters(inventoryId, branchId, productId, productVariantId, type, pageable);
        Page<StockMovementResponse> dtoPage = pageData.map(stockMovementMapper::toResponse);
        return PageResponse.fromPage(dtoPage);
    }

    private Inventory getOrCreateInventory(Long variantId, Long branchId) {
        return inventoryRepository.findByProductVariantIdAndBranchId(variantId, branchId)
                .orElseGet(() -> {
                    ProductVariant variant = productVariantRepository.findById(variantId)
                            .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIANT_NOT_FOUND));
                    Branch branch = branchRepository.findById(branchId)
                            .orElseThrow(() -> new AppException(ErrorCode.BRANCH_NOT_FOUND));
                    return inventoryRepository.save(Inventory.builder()
                            .productVariant(variant)
                            .branch(branch)
                            .quantity(0L)
                            .build());
                });
    }

    private void createMovement(Inventory inventory, StockMovementType type, Long quantity, Long refId, String note) {
        StockMovement movement = StockMovement.builder()
                .inventory(inventory)
                .type(type)
                .quantity(quantity)
                .referenceId(refId)
                .note(note)
                .build();
        stockMovementRepository.save(movement);
    }

}
