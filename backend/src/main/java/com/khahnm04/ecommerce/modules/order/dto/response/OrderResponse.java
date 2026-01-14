package com.khahnm04.ecommerce.modules.order.dto.response;

import com.khahnm04.ecommerce.shared.enums.OrderStatus;
import com.khahnm04.ecommerce.shared.enums.PaymentMethod;
import com.khahnm04.ecommerce.shared.enums.PaymentStatus;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private String orderCode;
    private Long userId;

    // Thông tin giao hàng
    private String shippingName;
    private String shippingPhone;
    private String fullAddress; // Ghép từ Tỉnh + Huyện + Xã + Chi tiết
    private String note;

    // Tài chính
    private Long totalAmount;      // Tổng tiền hàng
    private Long discountAmount;   // Tiền giảm giá
    private Long finalAmount;      // Khách phải trả

    // Trạng thái
    private OrderStatus orderStatus;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;

    private LocalDateTime createdAt;

    // Danh sách sản phẩm
    private List<OrderDetailResponse> orderDetails;

}
