package com.khahnm04.ecommerce.modules.coupon.mapper;

import com.khahnm04.ecommerce.modules.coupon.dto.request.CouponRequest;
import com.khahnm04.ecommerce.modules.coupon.dto.response.CouponResponse;
import com.khahnm04.ecommerce.modules.coupon.entity.Coupon;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CouponMapper {

    Coupon toCoupon(CouponRequest request);

    CouponResponse toCouponResponse(Coupon coupon);

    List<CouponResponse> toCouponResponseList(List<Coupon> coupons);

    // Dùng để update coupon
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true) // Thường code không cho sửa
    void updateCoupon(@MappingTarget Coupon coupon, CouponRequest request);

}
