package com.khahnm04.ecommerce.modules.product.mapper;

import com.khahnm04.ecommerce.modules.product.dto.request.VariantRequest;
import com.khahnm04.ecommerce.modules.product.dto.response.VariantResponse;
import com.khahnm04.ecommerce.modules.product.entity.Variant;
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
