package com.khahnm04.ecommerce.mapper;

import com.khahnm04.ecommerce.dto.request.order.FeedbackRequest;
import com.khahnm04.ecommerce.dto.response.order.FeedbackResponse;
import com.khahnm04.ecommerce.entity.order.Feedback;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {

    Feedback toFeedback(FeedbackRequest request);

    @Mapping(source = "orderDetail.order.user.fullName", target = "userName")
    @Mapping(source = "orderDetail.productVariant.product.name", target = "productName")
    FeedbackResponse toFeedbackResponse(Feedback feedback);

}
