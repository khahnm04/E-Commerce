package com.khahnm04.ecommerce.dto.request.inventory;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
public class InventoryRequest {

    @NotNull(message = "Variant ID is required")
    private Long productVariantId;

    @NotNull(message = "Branch ID is required")
    private Long branchId;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity must be >= 0")
    private Long quantity;

}
