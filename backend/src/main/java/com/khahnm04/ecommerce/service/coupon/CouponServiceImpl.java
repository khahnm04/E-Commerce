package com.khahnm04.ecommerce.service.coupon;

import com.khahnm04.ecommerce.common.enums.DiscountType;
import com.khahnm04.ecommerce.dto.request.coupon.CouponRequest;
import com.khahnm04.ecommerce.dto.response.coupon.CouponResponse;
import com.khahnm04.ecommerce.entity.coupon.Coupon;
import com.khahnm04.ecommerce.exception.AppException;
import com.khahnm04.ecommerce.exception.ErrorCode;
import com.khahnm04.ecommerce.mapper.CouponMapper;
import com.khahnm04.ecommerce.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final CouponMapper couponMapper;

    @Override
    public CouponResponse createCoupon(CouponRequest request) {
        if (couponRepository.existsByCode(request.getCode())) {
            throw new AppException(ErrorCode.COUPON_ALREADY_EXISTS);
        }
        Coupon coupon = couponMapper.toCoupon(request);
        coupon.setActive(true);
        return couponMapper.toCouponResponse(couponRepository.save(coupon));
    }

    @Override
    public List<CouponResponse> getAllCoupons() {
        return couponMapper.toCouponResponseList(couponRepository.findAll());
    }

    @Override
    public BigDecimal calculateCouponValue(String code, BigDecimal totalAmount) {
        Coupon coupon = couponRepository.findByCode(code)
                .orElseThrow(() -> new AppException(ErrorCode.COUPON_NOT_FOUND));

        // 1. Check Active
        if (!coupon.isActive()) {
            throw new AppException(ErrorCode.COUPON_NOT_ACTIVE);
        }

        // 2. Check số lượng sử dụng
        if (coupon.getMaxUsage() != null && coupon.getUsageCount() >= coupon.getMaxUsage()) {
            throw new AppException(ErrorCode.COUPON_EXPIRED); // Hết lượt dùng
        }

        // 3. Check ngày hết hạn
        LocalDateTime now = LocalDateTime.now();
        if (coupon.getEndDate() != null && now.isAfter(coupon.getEndDate())) {
            throw new AppException(ErrorCode.COUPON_EXPIRED);
        }
        if (coupon.getStartDate() != null && now.isBefore(coupon.getStartDate())) {
            throw new AppException(ErrorCode.COUPON_NOT_YET_VALID);
        }

        // 4. Check giá trị đơn hàng tối thiểu
        if (coupon.getMinOrderValue() != null && totalAmount.compareTo(coupon.getMinOrderValue()) < 0) {
            throw new AppException(ErrorCode.COUPON_MIN_ORDER_NOT_MET);
        }

        // 5. Tính toán số tiền giảm
        BigDecimal discountAmount = BigDecimal.ZERO;
        if (coupon.getDiscountType() == DiscountType.PERCENT) {
            // Giảm theo %: total * value / 100
            discountAmount = totalAmount.multiply(coupon.getDiscountValue()).divide(BigDecimal.valueOf(100));
        } else if (coupon.getDiscountType() == DiscountType.FIXED_AMOUNT) {
            // Giảm tiền cố định
            discountAmount = coupon.getDiscountValue();
        }

        return discountAmount;
    }

}
