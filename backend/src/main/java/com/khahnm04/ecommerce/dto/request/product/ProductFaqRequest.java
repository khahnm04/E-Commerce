package com.khahnm04.ecommerce.dto.request.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductFaqRequest implements Serializable {

    @NotNull(message = "productId is required")
    private Long productId;

    @NotBlank(message = "question cannot be blank")
    private String question;

    @NotBlank(message = "answer cannot be blank")
    private String answer;

    @Min(value = 0, message = "displayOrder must be >= 0")
    private Integer displayOrder;

}
