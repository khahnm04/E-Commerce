package com.khahnm04.ecommerce.modules.order.service.impl;

import com.khahnm04.ecommerce.shared.enums.OrderStatus;
import com.khahnm04.ecommerce.modules.order.dto.request.FeedbackRequest;
import com.khahnm04.ecommerce.modules.order.dto.response.FeedbackResponse;
import com.khahnm04.ecommerce.modules.order.entity.Feedback;
import com.khahnm04.ecommerce.modules.order.entity.OrderDetail;
import com.khahnm04.ecommerce.modules.user.entity.User;
import com.khahnm04.ecommerce.core.exception.AppException;
import com.khahnm04.ecommerce.core.exception.ErrorCode;
import com.khahnm04.ecommerce.modules.order.mapper.FeedbackMapper;
import com.khahnm04.ecommerce.modules.order.repository.FeedbackRepository;
import com.khahnm04.ecommerce.modules.order.repository.OrderDetailRepository;
import com.khahnm04.ecommerce.modules.order.service.FeedbackService;
import com.khahnm04.ecommerce.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final UserService userService;
    private final FeedbackMapper feedbackMapper;

    @Override
    @Transactional
    public FeedbackResponse createFeedback(FeedbackRequest request) {
        User user = userService.getCurrentUser();

        // 1. Tìm OrderDetail
        OrderDetail orderDetail = orderDetailRepository.findById(request.getOrderDetailId())
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND)); // Reuse ErrorCode

        // 2. Check quyền sở hữu (User này có mua đơn này không?)
        if (!orderDetail.getOrder().getUser().getId().equals(user.getId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        // 3. Check trạng thái đơn hàng (Phải giao xong mới được review)
        if (orderDetail.getOrder().getOrderStatus() != OrderStatus.DELIVERED) {
            throw new AppException(ErrorCode.FEEDBACK_NOT_ALLOWED); // Cần thêm ErrorCode này
        }

        // 4. Check xem đã đánh giá chưa
        if (feedbackRepository.existsByOrderDetailId(orderDetail.getId())) {
            throw new AppException(ErrorCode.FEEDBACK_ALREADY_EXISTS); // Cần thêm ErrorCode này
        }

        // 5. Lưu
        Feedback feedback = feedbackMapper.toFeedback(request);
        feedback.setOrderDetail(orderDetail);

        return feedbackMapper.toFeedbackResponse(feedbackRepository.save(feedback));
    }

    @Override
    public Page<FeedbackResponse> getFeedbacksByProductId(Long productId, Pageable pageable) {
        return feedbackRepository.findByProductId(productId, pageable)
                .map(feedbackMapper::toFeedbackResponse);
    }

}
