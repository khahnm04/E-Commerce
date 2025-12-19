package com.khahnm04.ecommerce.service.order;

import com.khahnm04.ecommerce.dto.request.order.FeedbackRequest;
import com.khahnm04.ecommerce.dto.response.order.FeedbackResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FeedbackService {

    /**
     * Tạo mới một đánh giá cho sản phẩm trong đơn hàng
     * Điều kiện: Đơn hàng phải giao thành công và chưa được đánh giá trước đó
     */
    FeedbackResponse createFeedback(FeedbackRequest request);

    /**
     * Lấy danh sách đánh giá của một sản phẩm cụ thể
     * Dùng để hiển thị ở trang chi tiết sản phẩm
     */
    Page<FeedbackResponse> getFeedbacksByProductId(Long productId, Pageable pageable);

}
