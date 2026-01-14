package com.khahnm04.ecommerce.modules.inventory.mapper;

import com.khahnm04.ecommerce.modules.inventory.dto.response.StockMovementResponse;
import com.khahnm04.ecommerce.modules.inventory.entity.StockMovement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StockMovementMapper {

    @Mapping(source = "inventory.id", target = "inventoryId")
    @Mapping(source = "inventory.productVariant.product.name", target = "productName")
    @Mapping(source = "inventory.productVariant.sku", target = "sku")
    @Mapping(source = "inventory.productVariant.image", target = "productImage")
    @Mapping(source = "inventory.branch.name", target = "branchName")
    StockMovementResponse toResponse(StockMovement stockMovement);

}
