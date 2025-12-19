package com.khahnm04.ecommerce.controller.client;

import com.khahnm04.ecommerce.dto.request.order.FeedbackRequest;
import com.khahnm04.ecommerce.dto.response.ApiResponse;
import com.khahnm04.ecommerce.dto.response.order.FeedbackResponse;
import com.khahnm04.ecommerce.service.order.FeedbackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    // 1. Tạo đánh giá mới
    @PostMapping
    public ApiResponse<FeedbackResponse> createFeedback(@Valid @RequestBody FeedbackRequest request) {
        return ApiResponse.<FeedbackResponse>builder()
                .data(feedbackService.createFeedback(request))
                .message("Đánh giá sản phẩm thành công")
                .build();
    }

    // 2. Xem đánh giá của 1 sản phẩm (Công khai - Ai cũng xem được)
    @GetMapping("/product/{productId}")
    public ApiResponse<Page<FeedbackResponse>> getProductFeedbacks(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ApiResponse.<Page<FeedbackResponse>>builder()
                .data(feedbackService.getFeedbacksByProductId(productId, pageable))
                .message("Lấy danh sách đánh giá thành công")
                .build();
    }

}
