package com.khahnm04.ecommerce.modules.product.mapper;

import com.khahnm04.ecommerce.modules.product.dto.request.VariantValueRequest;
import com.khahnm04.ecommerce.modules.product.dto.response.VariantValueResponse;
import com.khahnm04.ecommerce.modules.product.entity.VariantValue;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface VariantValueMapper {

    @Mapping(target = "variant", ignore = true)
    VariantValue fromRequestToEntity(VariantValueRequest request);

    @Mapping(target = "variant", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget VariantValue entity, VariantValueRequest request);

    @Mapping(target = "variantId", source = "variant.id")
    @Mapping(target = "variantName", source = "variant.name")
    VariantValueResponse toResponse(VariantValue entity);

}
