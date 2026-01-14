package com.khahnm04.ecommerce.modules.cart.dto.response;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {

    private Long id;              // ID của dòng trong bảng cart_items
    private Long productVariantId;

    // Thông tin sản phẩm để hiển thị (Flatten từ ProductVariant và Product)
    private String productName;
    private String sku;
    private String image;         // Ảnh đại diện biến thể
    private String color;         // Ví dụ: Màu đỏ
    private String storage;       // Ví dụ: 256GB (nếu có attributes)

    // Thông tin giá và số lượng
    private BigDecimal price;     // Giá đơn vị (tại thời điểm xem giỏ)
    private Integer quantity;     // Số lượng mua
    private BigDecimal subTotal;  // Thành tiền = price * quantity

    private Long maxStock;        // Tồn kho hiện tại (để Frontend giới hạn không cho tăng quá số này)

}
