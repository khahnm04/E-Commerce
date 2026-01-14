package com.khahnm04.ecommerce.modules.coupon.controller;

import com.khahnm04.ecommerce.modules.coupon.dto.request.CouponRequest;
import com.khahnm04.ecommerce.shared.dto.ApiResponse;
import com.khahnm04.ecommerce.modules.coupon.dto.response.CouponResponse;
import com.khahnm04.ecommerce.modules.coupon.service.CouponService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/admin/coupons")
@RequiredArgsConstructor
public class AdminCouponController {

    private final CouponService couponService;

    // Tạo mã giảm giá mới
    @PostMapping
    public ApiResponse<CouponResponse> createCoupon(
            @Valid @RequestBody CouponRequest request
    ) {
        return ApiResponse.<CouponResponse>builder()
                .data(couponService.createCoupon(request))
                .message("Tạo mã giảm giá thành công")
                .build();
    }

    // Xem danh sách mã giảm giá
    @GetMapping
    public ApiResponse<List<CouponResponse>> getAllCoupons() {
        return ApiResponse.<List<CouponResponse>>builder()
                .data(couponService.getAllCoupons())
                .message("Lấy danh sách mã giảm giá thành công")
                .build();
    }

}
