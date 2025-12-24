package com.khahnm04.ecommerce.modules.banner.service;

import com.khahnm04.ecommerce.shared.enums.BannerPosition;
import com.khahnm04.ecommerce.shared.enums.BannerStatus;
import com.khahnm04.ecommerce.modules.banner.dto.request.BannerRequest;
import com.khahnm04.ecommerce.modules.banner.dto.response.BannerResponse;
import com.khahnm04.ecommerce.modules.banner.entity.Banner;
import com.khahnm04.ecommerce.core.exception.AppException;
import com.khahnm04.ecommerce.core.exception.ErrorCode;
import com.khahnm04.ecommerce.modules.banner.mapper.BannerMapper;
import com.khahnm04.ecommerce.modules.banner.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {

    private final BannerRepository bannerRepository;
    private final BannerMapper bannerMapper;

    @Override
    public List<BannerResponse> getAllBanners() {
        return bannerMapper.toBannerResponseList(bannerRepository.findAll());
    }

    @Override
    public BannerResponse getBannerById(Long id) {
        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BANNER_NOT_FOUND));
        return bannerMapper.toBannerResponse(banner);
    }

    @Override
    @Transactional
    public BannerResponse createBanner(BannerRequest request) {
        Banner banner = bannerMapper.toBanner(request);

        // Set default value nếu null (Status Active, Order = 0)
        if (banner.getStatus() == null) banner.setStatus(BannerStatus.ACTIVE);
        if (banner.getDisplayOrder() == null) banner.setDisplayOrder(0);

        return bannerMapper.toBannerResponse(bannerRepository.save(banner));
    }

    @Override
    @Transactional
    public BannerResponse updateBanner(Long id, BannerRequest request) {
        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BANNER_NOT_FOUND));

        // MapStruct tự động cập nhật các trường từ request vào entity cũ
        bannerMapper.updateBanner(banner, request);

        return bannerMapper.toBannerResponse(bannerRepository.save(banner));
    }

    @Override
    @Transactional
    public void deleteBanner(Long id) {
        if (!bannerRepository.existsById(id)) {
            throw new AppException(ErrorCode.BANNER_NOT_FOUND);
        }
        bannerRepository.deleteById(id);
    }

    @Override
    public List<BannerResponse> getBannersByPosition(BannerPosition position) {
        // Chỉ lấy request đang ACTIVE và sắp xếp theo thứ tự ưu tiên (nhỏ -> lớn)
        List<Banner> banners = bannerRepository.findByPositionAndStatusOrderByDisplayOrderAsc(
                position, BannerStatus.ACTIVE
        );
        return bannerMapper.toBannerResponseList(banners);
    }

}
