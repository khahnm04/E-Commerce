package com.khahnm04.ecommerce.dto.response.inventory;

import com.khahnm04.ecommerce.common.enums.StockMovementType;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockMovementResponse {

    private Long id;
    private StockMovementType type;
    private Long quantity;
    private Long referenceId;
    private String note;
    private LocalDateTime createdAt;
    private String createdBy;
    private Long inventoryId;
    private String productName;
    private String sku;
    private String branchName;
    private String productImage;

}
