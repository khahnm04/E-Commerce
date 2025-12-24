package com.khahnm04.ecommerce.modules.coupon.repository;

import com.khahnm04.ecommerce.modules.coupon.entity.CouponUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponUsageRepository extends JpaRepository<CouponUsage, Long> {

    // Đếm số lần user X đã dùng coupon Y
    @Query("select count(c.id) from CouponUsage c where c.user.id = :userId and c.coupon.id = :userId")
    long countByUserIdAndCouponId(Long userId, Long couponId);

}
