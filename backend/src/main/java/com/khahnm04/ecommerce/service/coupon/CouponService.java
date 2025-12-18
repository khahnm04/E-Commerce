package com.khahnm04.ecommerce.service.coupon;

import com.khahnm04.ecommerce.dto.request.coupon.CouponRequest;
import com.khahnm04.ecommerce.dto.response.coupon.CouponResponse;

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
