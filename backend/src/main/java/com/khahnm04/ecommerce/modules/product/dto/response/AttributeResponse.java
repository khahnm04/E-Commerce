package com.khahnm04.ecommerce.modules.product.dto.response;

import com.khahnm04.ecommerce.shared.dto.BaseResponse;
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
public class AttributeResponse extends BaseResponse<Long> {

    private String name;
    private String code;
    private String description;
    private String value;

}
