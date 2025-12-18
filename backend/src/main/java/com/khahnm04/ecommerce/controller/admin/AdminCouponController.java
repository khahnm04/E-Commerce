package com.khahnm04.ecommerce.controller.admin;

import com.khahnm04.ecommerce.dto.request.coupon.CouponRequest;
import com.khahnm04.ecommerce.dto.response.ApiResponse;
import com.khahnm04.ecommerce.dto.response.coupon.CouponResponse;
import com.khahnm04.ecommerce.service.coupon.CouponService;
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
