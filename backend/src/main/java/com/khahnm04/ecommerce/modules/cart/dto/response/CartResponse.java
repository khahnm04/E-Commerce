package com.khahnm04.ecommerce.modules.cart.dto.response;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {

    private Long id;
    private Long userId;

    // Tổng hợp thông tin
    private Integer totalItems;       // Tổng số lượng sản phẩm (ví dụ: 5 cái)
    private BigDecimal totalPrice;    // Tổng tiền tạm tính (chưa ship/discount)

    private List<CartItemResponse> items; // Danh sách chi tiết

}
