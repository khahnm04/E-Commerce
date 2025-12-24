package com.khahnm04.ecommerce.modules.category.mapper;

import com.khahnm04.ecommerce.modules.category.dto.request.CategoryRequest;
import com.khahnm04.ecommerce.modules.category.dto.response.CategoryResponse;
import com.khahnm04.ecommerce.modules.category.entity.Category;
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
