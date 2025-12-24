package com.khahnm04.ecommerce.modules.order.controller;

import com.khahnm04.ecommerce.shared.enums.OrderStatus;
import com.khahnm04.ecommerce.shared.dto.ApiResponse;
import com.khahnm04.ecommerce.modules.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final OrderService orderService;

    // Cập nhật trạng thái đơn hàng (VD: Xác nhận, Đang giao...)
    // PUT /api/v1/admin/orders/{id}/status?status=SHIPPED
    @PutMapping("/{id}/status")
    public ApiResponse<Void> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status
    ) {
        orderService.updateOrderStatus(id, status);
        return ApiResponse.<Void>builder()
                .message("Cập nhật trạng thái đơn hàng thành công: " + status)
                .build();
    }

}
