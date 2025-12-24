package com.khahnm04.ecommerce.modules.banner.controller;

import com.khahnm04.ecommerce.shared.enums.BannerPosition;
import com.khahnm04.ecommerce.shared.dto.ApiResponse;
import com.khahnm04.ecommerce.modules.banner.dto.response.BannerResponse;
import com.khahnm04.ecommerce.modules.banner.service.BannerService;
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
                .message("Lấy danh sách request thành công")
                .build();
    }

}
