package com.khahnm04.ecommerce.mapper;

import com.khahnm04.ecommerce.dto.request.product.ProductFaqRequest;
import com.khahnm04.ecommerce.dto.response.product.ProductFaqResponse;
import com.khahnm04.ecommerce.entity.product.ProductFaq;
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
