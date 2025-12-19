package com.khahnm04.ecommerce.controller.admin;

import com.khahnm04.ecommerce.dto.request.banner.BannerRequest;
import com.khahnm04.ecommerce.dto.response.ApiResponse;
import com.khahnm04.ecommerce.dto.response.banner.BannerResponse;
import com.khahnm04.ecommerce.service.banner.BannerService;
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
                .message("Lấy danh sách banner thành công")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<BannerResponse> getBannerById(@PathVariable Long id) {
        return ApiResponse.<BannerResponse>builder()
                .data(bannerService.getBannerById(id))
                .message("Lấy chi tiết banner thành công")
                .build();
    }

    @PostMapping
    public ApiResponse<BannerResponse> createBanner(@Valid @RequestBody BannerRequest request) {
        return ApiResponse.<BannerResponse>builder()
                .data(bannerService.createBanner(request))
                .message("Tạo banner thành công")
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<BannerResponse> updateBanner(@PathVariable Long id, @Valid @RequestBody BannerRequest request) {
        return ApiResponse.<BannerResponse>builder()
                .data(bannerService.updateBanner(id, request))
                .message("Cập nhật banner thành công")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteBanner(@PathVariable Long id) {
        bannerService.deleteBanner(id);
        return ApiResponse.<Void>builder()
                .message("Xóa banner thành công")
                .build();
    }

}
