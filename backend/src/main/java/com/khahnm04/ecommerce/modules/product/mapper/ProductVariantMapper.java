package com.khahnm04.ecommerce.modules.product.mapper;

import com.khahnm04.ecommerce.modules.product.dto.request.ProductVariantRequest;
import com.khahnm04.ecommerce.modules.product.dto.response.ProductVariantResponse;
import com.khahnm04.ecommerce.modules.product.dto.response.VariantValueResponse;
import com.khahnm04.ecommerce.modules.product.entity.ProductVariant;
import com.khahnm04.ecommerce.modules.product.entity.ProductVariantValue;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductVariantMapper {

    @Mapping(target = "image", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "productVariantValues", ignore = true)
    ProductVariant fromRequestToEntity(ProductVariantRequest request);

    @Mapping(target = "image", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "productVariantValues", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget ProductVariant entity, ProductVariantRequest request);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "variantValues", source = "productVariantValues")
    ProductVariantResponse toResponse(ProductVariant entity);

    @Mapping(source = "variantValue.id", target = "id")
    @Mapping(source = "variantValue.variant.id", target = "variantId")
    @Mapping(source = "variantValue.variant.name", target = "variantName")
    @Mapping(source = "variantValue.value", target = "value")
    @Mapping(source = "variantValue.code", target = "code")
    @Mapping(source = "variantValue.displayOrder", target = "displayOrder")
    VariantValueResponse mapVariantValue(ProductVariantValue pvv);

}
