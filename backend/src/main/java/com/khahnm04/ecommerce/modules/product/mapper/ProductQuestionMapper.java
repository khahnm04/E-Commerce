package com.khahnm04.ecommerce.modules.product.mapper;

import com.khahnm04.ecommerce.modules.product.dto.request.ProductQuestionRequest;
import com.khahnm04.ecommerce.modules.product.dto.response.ProductQuestionResponse;
import com.khahnm04.ecommerce.modules.product.entity.ProductQuestion;
import com.khahnm04.ecommerce.modules.user.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductQuestionMapper {

    @Mapping(target = "replies", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "isAdminReply", ignore = true)
    ProductQuestion toEntity(ProductQuestionRequest request);

    @Mapping(target = "user", source = "user", qualifiedByName = "mapUserSummary")
    @Mapping(target = "parentId", source = "parent.id")
    @Mapping(target = "productId", source = "product.id")
    ProductQuestionResponse toResponse(ProductQuestion entity);

    @Named("mapUserSummary")
    default ProductQuestionResponse.UserSummary mapUserSummary(User user) {
        if (user == null) return null;
        boolean isAdmin = user.getRoles().stream()
                .anyMatch(r -> r.getName().equalsIgnoreCase("ADMIN") || r.getName().equalsIgnoreCase("STAFF"));
        return ProductQuestionResponse.UserSummary.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .isAdmin(isAdmin)
                .build();
    }

}
