package com.khahnm04.ecommerce.modules.category.dto.request;

import com.khahnm04.ecommerce.shared.enums.CategoryStatus;
import com.khahnm04.ecommerce.shared.validation.enums.ValidEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest implements Serializable {

    @NotBlank(message = "name cannot be blank")
    private String name;

    @NotBlank(message = "slug cannot be blank")
    private String slug;

    private String description;

    private MultipartFile image;

    @ValidEnum(name = "status", enumClass = CategoryStatus.class)
    private String status;

    private Long parentId;

}
