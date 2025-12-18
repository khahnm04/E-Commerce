package com.khahnm04.ecommerce.mapper;

import com.khahnm04.ecommerce.dto.response.inventory.InventoryResponse;
import com.khahnm04.ecommerce.entity.inventory.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    @Mapping(source = "productVariant.id", target = "productVariantId")
    @Mapping(source = "productVariant.sku", target = "sku")
    @Mapping(source = "branch.id", target = "branchId")
    @Mapping(source = "branch.name", target = "branchName")
    InventoryResponse toInventoryResponse(Inventory inventory);

    List<InventoryResponse> toInventoryResponseList(List<Inventory> inventories);

}
