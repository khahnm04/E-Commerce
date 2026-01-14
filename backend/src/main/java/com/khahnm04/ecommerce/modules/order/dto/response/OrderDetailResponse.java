package com.khahnm04.ecommerce.modules.order.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailResponse {

    private Long id;
    private Long productVariantId;
    private String productName;
    private String productSku;
    private String productImage;

    private Long unitPrice; // Giá lúc mua
    private Long quantity;
    private Long subTotal;  // unitPrice * quantity

    private boolean hasFeedback; // Đã đánh giá chưa?

}
