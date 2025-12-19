package com.khahnm04.ecommerce.mapper;

import com.khahnm04.ecommerce.dto.request.banner.BannerRequest;
import com.khahnm04.ecommerce.dto.response.banner.BannerResponse;
import com.khahnm04.ecommerce.entity.content.Banner;
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
