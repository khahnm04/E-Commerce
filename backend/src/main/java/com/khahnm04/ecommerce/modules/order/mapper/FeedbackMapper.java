package com.khahnm04.ecommerce.modules.order.mapper;

import com.khahnm04.ecommerce.modules.order.dto.request.FeedbackRequest;
import com.khahnm04.ecommerce.modules.order.dto.response.FeedbackResponse;
import com.khahnm04.ecommerce.modules.order.entity.Feedback;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {

    Feedback toFeedback(FeedbackRequest request);

    @Mapping(source = "orderDetail.order.user.fullName", target = "userName")
    @Mapping(source = "orderDetail.productVariant.product.name", target = "productName")
    FeedbackResponse toFeedbackResponse(Feedback feedback);

}
