package com.khahnm04.ecommerce.repository;

import com.khahnm04.ecommerce.entity.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    // Tìm coupon theo mã code (Ví dụ: SALE50)
    Optional<Coupon> findByCode(String code);

    // Kiểm tra xem mã này đã tồn tại chưa (Dùng khi tạo mới để tránh trùng)
    boolean existsByCode(String code);

}
