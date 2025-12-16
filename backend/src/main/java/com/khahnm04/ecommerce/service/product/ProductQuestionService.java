package com.khahnm04.ecommerce.service.product;

import com.khahnm04.ecommerce.dto.request.product.ProductQuestionRequest;
import com.khahnm04.ecommerce.dto.response.product.ProductQuestionResponse;

public interface ProductQuestionService {

    ProductQuestionResponse createQuestion(ProductQuestionRequest request);

}
