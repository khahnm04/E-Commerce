package com.khahnm04.ecommerce.modules.banner.service;

import com.khahnm04.ecommerce.shared.enums.BannerPosition;
import com.khahnm04.ecommerce.modules.banner.dto.request.BannerRequest;
import com.khahnm04.ecommerce.modules.banner.dto.response.BannerResponse;
import java.util.List;

public interface BannerService {

    // --- ADMIN METHODS ---
    List<BannerResponse> getAllBanners();

    BannerResponse getBannerById(Long id);

    BannerResponse createBanner(BannerRequest request);

    BannerResponse updateBanner(Long id, BannerRequest request);

    void deleteBanner(Long id);

    // --- STOREFRONT METHODS ---
    List<BannerResponse> getBannersByPosition(BannerPosition position);

}
