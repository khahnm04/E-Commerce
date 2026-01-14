package com.khahnm04.ecommerce.modules.category.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignProductToCategoryRequest implements Serializable {

    @NotEmpty(message = "productIds cannot be empty")
    private Set<Long> productIds;

}
