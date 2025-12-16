package com.khahnm04.ecommerce.mapper;

import com.khahnm04.ecommerce.dto.request.product.VariantRequest;
import com.khahnm04.ecommerce.dto.response.product.VariantResponse;
import com.khahnm04.ecommerce.entity.product.Variant;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface VariantMapper {

    Variant fromVariantRequestToVariant(VariantRequest variantRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateVariant(@MappingTarget Variant variant, VariantRequest variantRequest);

    VariantResponse fromVariantToVariantResponse(Variant variant);

}
