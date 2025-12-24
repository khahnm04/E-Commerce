package com.khahnm04.ecommerce.modules.product.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProductQuestionResponse implements Serializable {

    private Long id;
    private String content;
    private Long parentId;
    private Long productId;
    private Boolean isAdminReply;
    private LocalDateTime createdAt;
    private UserSummary user;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserSummary {
        private Long id;
        private String fullName;
        private boolean isAdmin;
    }

}
