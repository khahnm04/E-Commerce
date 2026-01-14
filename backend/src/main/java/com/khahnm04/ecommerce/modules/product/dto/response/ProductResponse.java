package com.khahnm04.ecommerce.modules.product.dto.response;

import com.khahnm04.ecommerce.shared.dto.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse extends BaseResponse<Long> {

    private String name;
    private String slug;
    private Long price;
    private Long oldPrice;
    private String thumbnail;
    private String description;
    private String status;
    private BrandSummary brand;
    private List<CategorySummary> categories;
    private List<ProductImageResponse> images;
    private List<AttributeResponse> attributes;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BrandSummary {
        private Long id;
        private String name;
        private String slug;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategorySummary {
        private Long id;
        private String name;
        private String slug;
    }

}
