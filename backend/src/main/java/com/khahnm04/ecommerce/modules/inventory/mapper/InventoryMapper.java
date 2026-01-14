package com.khahnm04.ecommerce.modules.inventory.mapper;

import com.khahnm04.ecommerce.modules.inventory.dto.response.InventoryResponse;
import com.khahnm04.ecommerce.modules.inventory.entity.Inventory;
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
