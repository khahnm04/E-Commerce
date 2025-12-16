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
public class ProductFaqResponse extends BaseResponse<Long> {

    private Long productId;
    private String productName;
    private String question;
    private String answer;
    private Integer displayOrder;

}
