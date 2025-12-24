package com.khahnm04.ecommerce.modules.banner.dto.request;

import com.khahnm04.ecommerce.shared.enums.BannerPosition;
import com.khahnm04.ecommerce.shared.enums.BannerStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BannerRequest {

    @NotBlank(message = "Tên request không được để trống")
    private String name;

    @NotBlank(message = "Ảnh request không được để trống")
    private String image;

    private String linkUrl;

    @NotNull(message = "Vị trí không được để trống")
    private BannerPosition position;

    private Integer displayOrder;

    private BannerStatus status;

}
