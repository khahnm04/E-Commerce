package com.khahnm04.ecommerce.dto.request.product;

import com.khahnm04.ecommerce.dto.response.product.VariantValueResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VariantValueRequest implements Serializable {


    @NotBlank(message = "value cannot be blank")
    private String value;

    private String code;

    private Integer displayOrder;

    @NotNull(message = "variantId is required")
    private Long variantId;

}
