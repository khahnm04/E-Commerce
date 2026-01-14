package com.khahnm04.ecommerce.modules.product.mapper;

import com.khahnm04.ecommerce.modules.product.dto.request.ProductRequest;
import com.khahnm04.ecommerce.modules.product.dto.response.AttributeResponse;
import com.khahnm04.ecommerce.modules.product.dto.response.ProductResponse;
import com.khahnm04.ecommerce.modules.brand.entity.Brand;
import com.khahnm04.ecommerce.modules.category.entity.Category;
import com.khahnm04.ecommerce.modules.product.entity.Product;
import com.khahnm04.ecommerce.modules.product.entity.ProductAttributeValue;
import org.mapstruct.*;
import org.springframework.util.CollectionUtils;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "thumbnail", ignore = true)
    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "productCategories", ignore = true)
    @Mapping(target = "productImages", ignore = true)
    @Mapping(target = "productAttributeValues", ignore = true)
    @Mapping(target = "productVariants", ignore = true)
    Product fromProductRequestToProduct(ProductRequest request);

    @Mapping(target = "thumbnail", ignore = true)
    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "productCategories", ignore = true)
    @Mapping(target = "productImages", ignore = true)
    @Mapping(target = "productAttributeValues", ignore = true)
    @Mapping(target = "productVariants", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProduct(@MappingTarget Product product, ProductRequest request);

    @Mapping(target = "brand", source = "brand")
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "images", source = "productImages")
    @Mapping(target = "attributes", source = "productAttributeValues")
    ProductResponse fromProductToProductResponse(Product product);

    ProductResponse.BrandSummary toBrandSummary(Brand brand);

    @AfterMapping
    default void mapCategories(@MappingTarget ProductResponse.ProductResponseBuilder<?, ?> builder, Product product) {
        if (!CollectionUtils.isEmpty(product.getProductCategories())) {
            builder.categories(product.getProductCategories().stream()
                    .map(pc -> {
                        Category category = pc.getCategory();
                        return ProductResponse.CategorySummary.builder()
                                .id(category.getId())
                                .name(category.getName())
                                .slug(category.getSlug())
                                .build();
                    })
                    .toList());
        }
    }

    @Mapping(source = "attribute.id", target = "id")
    @Mapping(source = "attribute.name", target = "name")
    @Mapping(source = "attribute.code", target = "code")
    @Mapping(source = "attribute.description", target = "description")
    @Mapping(source = "value", target = "value")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    AttributeResponse mapAttributeValue(ProductAttributeValue pav);

}
