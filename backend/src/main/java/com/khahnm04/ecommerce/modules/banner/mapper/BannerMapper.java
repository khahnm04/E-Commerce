package com.khahnm04.ecommerce.modules.banner.mapper;

import com.khahnm04.ecommerce.modules.banner.dto.request.BannerRequest;
import com.khahnm04.ecommerce.modules.banner.dto.response.BannerResponse;
import com.khahnm04.ecommerce.modules.banner.entity.Banner;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BannerMapper {

    Banner toBanner(BannerRequest request);

    BannerResponse toBannerResponse(Banner banner);

    List<BannerResponse> toBannerResponseList(List<Banner> banners);

    // Update entity từ request (Bỏ qua ID và Audit fields)
    void updateBanner(@MappingTarget Banner banner, BannerRequest request);

}
