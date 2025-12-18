package com.khahnm04.ecommerce.repository;

import com.khahnm04.ecommerce.entity.order.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    // Kiểm tra xem sản phẩm trong đơn hàng này đã được đánh giá chưa
    boolean existsByOrderDetailId(Long orderDetailId);

}
