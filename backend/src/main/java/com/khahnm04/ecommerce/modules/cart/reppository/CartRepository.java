package com.khahnm04.ecommerce.modules.cart.reppository;

import com.khahnm04.ecommerce.modules.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    // Tìm giỏ hàng của một User cụ thể
    @Query("select c from Cart c where c.user.id = :userId")
    Optional<Cart> findByUserId(Long userId);

    // Kiểm tra xem User này đã có giỏ hàng chưa (Dùng khi tạo mới)
    @Query("select (count(c) > 0) from Cart c where c.user.id = ?1")
    boolean existsByUserId(Long userId);

}
