package com.khahnm04.ecommerce.mapper;

import com.khahnm04.ecommerce.dto.request.category.CategoryRequest;
import com.khahnm04.ecommerce.dto.response.category.CategoryResponse;
import com.khahnm04.ecommerce.entity.category.Category;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "image", ignore = true)
    Category fromCategoryRequestToCategory(CategoryRequest request);

    default Long getParentId(Category category) {
        return category.getParent() != null ? category.getParent().getId() : null;
    }

    @Mapping(target = "parentId", expression = "java(getParentId(category))")
    CategoryResponse toCategoryResponse(Category category);

    @Mapping(target = "image", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCategory(@MappingTarget Category category, CategoryRequest request);

}
