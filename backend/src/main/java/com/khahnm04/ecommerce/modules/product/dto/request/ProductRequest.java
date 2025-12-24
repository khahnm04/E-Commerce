package com.khahnm04.ecommerce.modules.product.dto.request;

import com.khahnm04.ecommerce.shared.enums.ProductStatus;
import com.khahnm04.ecommerce.shared.validation.enums.ValidEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest implements Serializable {

    @NotBlank(message = "name cannot be blank")
    private String name;

    @NotBlank(message = "slug cannot be blank")
    private String slug;

    @NotNull(message = "price is required")
    @Min(value = 0, message = "price must be >= 0")
    private Long price;

    @Min(value = 0, message = "oldPrice must be >= 0")
    private Long oldPrice;

    @NotBlank(message = "description cannot be blank")
    private String description;

    @ValidEnum(name = "status", enumClass = ProductStatus.class)
    private String status;

    @NotNull(message = "brandId cannot be null")
    private Long brandId;

    @NotNull(message = "categoryIds cannot be null or blank")
    private Set<Long> categoryIds;

    @NotEmpty(message = "images cannot be blank")
    private List<String> images;

    @Valid
    @NotEmpty(message = "attributes cannot be blank")
    private List<ProductAttributeRequest> attributes;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductAttributeRequest {
        @NotNull(message = "Attribute ID is required")
        private Long attributeId;

        @NotBlank(message = "Attribute value cannot be blank")
        private String value;
    }

}
