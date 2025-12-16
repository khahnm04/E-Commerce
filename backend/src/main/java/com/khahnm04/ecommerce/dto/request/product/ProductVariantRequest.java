package com.khahnm04.ecommerce.dto.request.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantRequest implements Serializable {

    private String sku;

    @NotNull(message = "price is required")
    @Min(value = 0, message = "price must be >= 0")
    private Long price;

    @Min(value = 0, message = "oldPrice must be >= 0")
    private Long oldPrice;

    private MultipartFile image;

    @NotNull(message = "productId is required")
    private Long productId;

    @NotEmpty(message = "variantValueIds cannot be blank")
    private Set<Long> variantValueIds;

}
