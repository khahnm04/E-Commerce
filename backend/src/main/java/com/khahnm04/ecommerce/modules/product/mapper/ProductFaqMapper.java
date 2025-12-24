package com.khahnm04.ecommerce.modules.product.mapper;

import com.khahnm04.ecommerce.modules.product.dto.request.ProductFaqRequest;
import com.khahnm04.ecommerce.modules.product.dto.response.ProductFaqResponse;
import com.khahnm04.ecommerce.modules.product.entity.ProductFaq;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductFaqMapper {

    @Mapping(target = "product", ignore = true)
    ProductFaq fromRequestToEntity(ProductFaqRequest request);

    @Mapping(target = "product", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget ProductFaq entity, ProductFaqRequest request);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    ProductFaqResponse toResponse(ProductFaq entity);

}
