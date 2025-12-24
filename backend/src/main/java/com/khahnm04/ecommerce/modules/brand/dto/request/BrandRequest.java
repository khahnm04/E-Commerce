package com.khahnm04.ecommerce.modules.brand.dto.request;

import com.khahnm04.ecommerce.shared.enums.BrandStatus;
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
public class BrandRequest implements Serializable {

    @NotBlank(message = "name cannot be blank")
    private String name;

    @NotBlank(message = "slug cannot be blank")
    private String slug;

    private String description;

    private MultipartFile logo;

    private String country;

    @ValidEnum(name = "status", enumClass = BrandStatus.class)
    private String status;

}
