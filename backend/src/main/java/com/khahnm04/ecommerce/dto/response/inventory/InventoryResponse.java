package com.khahnm04.ecommerce.dto.response.inventory;

import lombok.*;

@Getter
@Setter
@Builder
public class InventoryResponse {

    private Long id;
    private Long productVariantId;
    private String variantName;
    private Long branchId;
    private String branchName;
    private Long quantity;

}
