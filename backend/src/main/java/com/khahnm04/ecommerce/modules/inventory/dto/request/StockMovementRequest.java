package com.khahnm04.ecommerce.modules.inventory.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockMovementRequest {

    @NotNull(message = "productVariantId cannot be null")
    private Long productVariantId;

    @NotNull(message = "branchId cannot be null")
    private Long branchId;

    @NotNull(message = "quantity cannot be null")
    @Min(value = 1, message = "quantity must be positive")
    private Long quantity;

    private Long referenceId;

    private String note;

}
