package com.khahnm04.ecommerce.modules.category.dto.request;

import com.khahnm04.ecommerce.shared.enums.CategoryStatus;
import com.khahnm04.ecommerce.shared.validation.enums.ValidEnum;
import lombok.*;

@Getter
@Setter
@Builder
public class UpdateStatusRequest {

    @ValidEnum(name = "status", enumClass = CategoryStatus.class)
    private String status;

}
