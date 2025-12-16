package com.khahnm04.ecommerce.mapper;

import com.khahnm04.ecommerce.dto.request.brand.BrandRequest;
import com.khahnm04.ecommerce.dto.response.brand.BrandResponse;
import com.khahnm04.ecommerce.entity.brand.Brand;
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
