package com.khahnm04.ecommerce.modules.banner.dto.response;

import com.khahnm04.ecommerce.shared.enums.BannerPosition;
import com.khahnm04.ecommerce.shared.enums.BannerStatus;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BannerResponse {

    private Long id;
    private String name;
    private String image;
    private String linkUrl;
    private BannerPosition position;
    private Integer displayOrder;
    private BannerStatus status;

}
