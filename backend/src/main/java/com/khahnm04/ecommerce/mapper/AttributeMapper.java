package com.khahnm04.ecommerce.mapper;

import com.khahnm04.ecommerce.dto.request.product.AttributeRequest;
import com.khahnm04.ecommerce.dto.response.product.AttributeResponse;
import com.khahnm04.ecommerce.entity.product.Attribute;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface AttributeMapper {

    Attribute fromAttributeRequestToAttribute(AttributeRequest attributeRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAttribute(@MappingTarget Attribute attribute, AttributeRequest attributeRequest);

    AttributeResponse fromAttributeToAttributeResponse(Attribute attribute);

}
