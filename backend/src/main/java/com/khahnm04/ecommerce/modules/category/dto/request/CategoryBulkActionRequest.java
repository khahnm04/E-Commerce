package com.khahnm04.ecommerce.modules.category.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryBulkActionRequest {

    @NotEmpty(message = "categoryIds cannot be null or empty")
    private Set<Long> categoryIds;

    @NotBlank(message = "action cannot be blank")
    private String action;

}
