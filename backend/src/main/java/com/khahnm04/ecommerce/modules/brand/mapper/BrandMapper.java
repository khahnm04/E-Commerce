package com.khahnm04.ecommerce.modules.brand.mapper;

import com.khahnm04.ecommerce.modules.brand.dto.request.BrandRequest;
import com.khahnm04.ecommerce.modules.brand.dto.response.BrandResponse;
import com.khahnm04.ecommerce.modules.brand.entity.Brand;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BrandMapper {

    @Mapping(target = "logo", ignore = true)
    Brand fromBrandRequestToBrand(BrandRequest request);

    @Mapping(target = "logo", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBrand(@MappingTarget Brand brand, BrandRequest request);

    BrandResponse toBrandResponse(Brand brand);

}
