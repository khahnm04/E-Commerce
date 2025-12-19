package com.khahnm04.ecommerce.service.banner;

import com.khahnm04.ecommerce.common.enums.BannerPosition;
import com.khahnm04.ecommerce.dto.request.banner.BannerRequest;
import com.khahnm04.ecommerce.dto.response.banner.BannerResponse;
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
