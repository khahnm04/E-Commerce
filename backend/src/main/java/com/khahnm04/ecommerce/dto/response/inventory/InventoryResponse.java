package com.khahnm04.ecommerce.dto.response.inventory;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class InventoryResponse {

    private Long id;
    private Long productVariantId;
    private String sku;
    private Long branchId;
    private String branchName;
    private Long quantity;

}
