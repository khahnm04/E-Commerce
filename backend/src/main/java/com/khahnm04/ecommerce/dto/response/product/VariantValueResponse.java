package com.khahnm04.ecommerce.dto.response.product;

import com.khahnm04.ecommerce.dto.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class VariantValueResponse extends BaseResponse<Long> {

    private Long variantId;
    private String variantName;
    private String value;
    private String code;
    private Integer displayOrder;

}
