package com.khahnm04.ecommerce.controller.client;

import com.khahnm04.ecommerce.common.enums.BannerPosition;
import com.khahnm04.ecommerce.dto.response.ApiResponse;
import com.khahnm04.ecommerce.dto.response.banner.BannerResponse;
import com.khahnm04.ecommerce.service.banner.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/banners")
@RequiredArgsConstructor
public class BannerController {

    private final BannerService bannerService;

    @GetMapping
    public ApiResponse<List<BannerResponse>> getBanners(
            @RequestParam(defaultValue = "HOME_SLIDER") BannerPosition position
    ) {
        return ApiResponse.<List<BannerResponse>>builder()
                .data(bannerService.getBannersByPosition(position))
                .message("Lấy danh sách banner thành công")
                .build();
    }

}
