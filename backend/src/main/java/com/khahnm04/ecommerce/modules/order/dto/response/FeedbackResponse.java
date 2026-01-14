package com.khahnm04.ecommerce.modules.order.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackResponse {

    private Long id;
    private String userName;   // Tên người đánh giá
    private Integer rating;
    private String content;
    private String productName; // Tên sản phẩm họ mua (có thể kèm size/màu)
    private LocalDateTime createdAt;

}
