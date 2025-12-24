package com.khahnm04.ecommerce.modules.product.dto.response;

import com.khahnm04.ecommerce.shared.dto.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantResponse extends BaseResponse<Long> {

    private Long productId;
    private String productName;
    private String sku;
    private Long price;
    private Long oldPrice;
    private String image;
    private List<VariantValueResponse> variantValues;

}
