package com.khahnm04.ecommerce.dto.request.banner;

import com.khahnm04.ecommerce.common.enums.BannerPosition;
import com.khahnm04.ecommerce.common.enums.BannerStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BannerRequest {

    @NotBlank(message = "Tên banner không được để trống")
    private String name;

    @NotBlank(message = "Ảnh banner không được để trống")
    private String image;

    private String linkUrl;

    @NotNull(message = "Vị trí không được để trống")
    private BannerPosition position;

    private Integer displayOrder;

    private BannerStatus status;

}
