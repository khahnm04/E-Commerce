package com.khahnm04.ecommerce.modules.order.service;

import com.khahnm04.ecommerce.shared.enums.OrderStatus;
import com.khahnm04.ecommerce.modules.order.dto.request.OrderRequest;
import com.khahnm04.ecommerce.modules.order.dto.response.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    // Khách hàng đặt hàng
    OrderResponse placeOrder(OrderRequest request);

    // Khách hàng xem lịch sử đơn hàng
    Page<OrderResponse> getMyOrders(Pageable pageable);

    // Khách hàng xem chi tiết 1 đơn
    OrderResponse getOrderDetail(String orderCode);

    // Khách hàng hủy đơn (Chỉ hủy được khi còn PENDING)
    void cancelOrder(String orderCode, String reason);

    // Admin cập nhật trạng thái đơn (VD: Từ PENDING -> SHIPPING)
    void updateOrderStatus(Long orderId, OrderStatus status);

}
