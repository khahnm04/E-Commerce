package com.khahnm04.ecommerce.modules.category.dto.response;

import com.khahnm04.ecommerce.shared.enums.CategoryStatus;
import com.khahnm04.ecommerce.shared.dto.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse extends BaseResponse<Long> {

    private String name;
    private String slug;
    private String description;
    private String image;
    private Long parentId;
    private Integer position;
    private String path;
    private CategoryStatus status;

}
