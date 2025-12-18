package com.khahnm04.ecommerce.controller.admin;

import com.khahnm04.ecommerce.common.enums.OrderStatus;
import com.khahnm04.ecommerce.dto.response.ApiResponse;
import com.khahnm04.ecommerce.service.order.OrderService;
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
