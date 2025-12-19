package com.khahnm04.ecommerce.repository;

import com.khahnm04.ecommerce.entity.order.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    boolean existsByOrderDetailId(Long orderDetailId);

    // Lấy tất cả feedback của 1 sản phẩm (Truy vấn ngược từ Feedback -> OrderDetail -> Variant -> Product)
    @Query("SELECT f FROM Feedback f WHERE f.orderDetail.productVariant.product.id = :productId")
    Page<Feedback> findByProductId(@Param("productId") Long productId, Pageable pageable);

}
