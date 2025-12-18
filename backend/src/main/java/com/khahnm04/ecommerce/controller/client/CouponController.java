package com.khahnm04.ecommerce.controller.client;

import com.khahnm04.ecommerce.dto.response.ApiResponse;
import com.khahnm04.ecommerce.service.coupon.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("${api.prefix}/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    // API: Kiểm tra và tính giá trị giảm giá
    // GET /api/v1/coupons/calculate?code=SALE50&totalAmount=500000
    @GetMapping("/calculate")
    public ApiResponse<BigDecimal> calculateDiscount(
            @RequestParam String code,
            @RequestParam BigDecimal totalAmount
    ) {
        BigDecimal discount = couponService.calculateCouponValue(code, totalAmount);
        return ApiResponse.<BigDecimal>builder()
                .data(discount)
                .message("Mã hợp lệ. Số tiền được giảm: " + discount)
                .build();
    }

}
