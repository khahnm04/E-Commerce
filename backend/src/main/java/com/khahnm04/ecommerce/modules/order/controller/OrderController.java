package com.khahnm04.ecommerce.modules.order.controller;

import com.khahnm04.ecommerce.modules.order.dto.request.OrderRequest;
import com.khahnm04.ecommerce.shared.dto.ApiResponse;
import com.khahnm04.ecommerce.modules.order.dto.response.OrderResponse;
import com.khahnm04.ecommerce.modules.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 1. Đặt hàng (Checkout)
    @PostMapping
    public ApiResponse<OrderResponse> placeOrder(
            @Valid @RequestBody OrderRequest request
    ) {
        return ApiResponse.<OrderResponse>builder()
                .data(orderService.placeOrder(request))
                .message("Đặt hàng thành công")
                .build();
    }

    // 2. Xem lịch sử đơn hàng của tôi
    @GetMapping
    public ApiResponse<Page<OrderResponse>> getMyOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ApiResponse.<Page<OrderResponse>>builder()
                .data(orderService.getMyOrders(pageable))
                .message("Lấy danh sách đơn hàng thành công")
                .build();
    }

    // 3. Xem chi tiết 1 đơn hàng
    @GetMapping("/{orderCode}")
    public ApiResponse<OrderResponse> getOrderDetail(
            @PathVariable String orderCode
    ) {
        return ApiResponse.<OrderResponse>builder()
                .data(orderService.getOrderDetail(orderCode))
                .message("Lấy chi tiết đơn hàng thành công")
                .build();
    }

    // 4. Hủy đơn hàng
    @PutMapping("/{orderCode}/cancel")
    public ApiResponse<Void> cancelOrder(
            @PathVariable String orderCode,
            @RequestParam(required = false, defaultValue = "Khách hàng hủy đơn") String reason
    ) {
        orderService.cancelOrder(orderCode, reason);
        return ApiResponse.<Void>builder()
                .message("Hủy đơn hàng thành công")
                .build();
    }

}
