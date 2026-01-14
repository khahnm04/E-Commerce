package com.khahnm04.ecommerce.modules.banner.controller;

import com.khahnm04.ecommerce.modules.banner.dto.request.BannerRequest;
import com.khahnm04.ecommerce.shared.dto.ApiResponse;
import com.khahnm04.ecommerce.modules.banner.dto.response.BannerResponse;
import com.khahnm04.ecommerce.modules.banner.service.BannerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/admin/banners")
@RequiredArgsConstructor
public class AdminBannerController {

    private final BannerService bannerService;

    @GetMapping
    public ApiResponse<List<BannerResponse>> getAllBanners() {
        return ApiResponse.<List<BannerResponse>>builder()
                .data(bannerService.getAllBanners())
                .message("Lấy danh sách request thành công")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<BannerResponse> getBannerById(@PathVariable Long id) {
        return ApiResponse.<BannerResponse>builder()
                .data(bannerService.getBannerById(id))
                .message("Lấy chi tiết request thành công")
                .build();
    }

    @PostMapping
    public ApiResponse<BannerResponse> createBanner(@Valid @RequestBody BannerRequest request) {
        return ApiResponse.<BannerResponse>builder()
                .data(bannerService.createBanner(request))
                .message("Tạo request thành công")
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<BannerResponse> updateBanner(@PathVariable Long id, @Valid @RequestBody BannerRequest request) {
        return ApiResponse.<BannerResponse>builder()
                .data(bannerService.updateBanner(id, request))
                .message("Cập nhật request thành công")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteBanner(@PathVariable Long id) {
        bannerService.deleteBanner(id);
        return ApiResponse.<Void>builder()
                .message("Xóa request thành công")
                .build();
    }

}
