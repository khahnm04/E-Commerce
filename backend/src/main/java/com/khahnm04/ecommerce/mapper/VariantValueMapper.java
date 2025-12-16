package com.khahnm04.ecommerce.mapper;

import com.khahnm04.ecommerce.dto.request.product.VariantValueRequest;
import com.khahnm04.ecommerce.dto.response.product.VariantValueResponse;
import com.khahnm04.ecommerce.entity.product.VariantValue;
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
