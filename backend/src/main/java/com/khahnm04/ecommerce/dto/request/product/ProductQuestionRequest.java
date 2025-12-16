package com.khahnm04.ecommerce.dto.request.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductQuestionRequest {

    @NotNull(message = "productId is required")
    private Long productId;

    private Long parentId;

    @NotBlank(message = "content cannot be blank")
    private String content;

}
