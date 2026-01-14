package com.khahnm04.ecommerce.modules.coupon.service;

import com.khahnm04.ecommerce.modules.coupon.dto.request.CouponRequest;
import com.khahnm04.ecommerce.modules.coupon.dto.response.CouponResponse;

import java.math.BigDecimal;
import java.util.List;

public interface CouponService {

    // Admin: Tạo mã
    CouponResponse createCoupon(CouponRequest request);

    // Admin: Lấy danh sách
    List<CouponResponse> getAllCoupons();

    // User: Tính toán số tiền được giảm
    // Input: Mã code + Tổng tiền đơn hàng
    // Output: Số tiền được giảm (Discount Amount)
    BigDecimal calculateCouponValue(String code, BigDecimal totalAmount);

}
