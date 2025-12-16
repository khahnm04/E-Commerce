package com.khahnm04.ecommerce.dto.response.category;

import com.khahnm04.ecommerce.common.enums.CategoryStatus;
import com.khahnm04.ecommerce.dto.response.BaseResponse;
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
    private String path;
    private CategoryStatus status;

}
