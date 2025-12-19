package com.khahnm04.ecommerce.repository;

import com.khahnm04.ecommerce.entity.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Tìm đơn hàng theo mã đơn (VD: ORD-2023-12345)
    Optional<Order> findByOrderCode(String orderCode);

    // Lấy danh sách đơn hàng của 1 user (Có phân trang) - Dùng cho lịch sử mua hàng
    @Query("select o from Order o where o.user.id = :userIduserId")
    Page<Order> findByUserId(Long userId, Pageable pageable);

}
