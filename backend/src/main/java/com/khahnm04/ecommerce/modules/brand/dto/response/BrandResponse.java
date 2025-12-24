package com.khahnm04.ecommerce.modules.brand.dto.response;

import com.khahnm04.ecommerce.shared.enums.BrandStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BrandResponse implements Serializable {

    private Long id;
    private String name;
    private String slug;
    private String description;
    private String logo;
    private String country;
    private BrandStatus status;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime updatedAt;
    private Long updatedBy;
    private LocalDateTime deletedAt;

}
