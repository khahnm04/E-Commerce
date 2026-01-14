package com.khahnm04.ecommerce.modules.product.service;

import com.khahnm04.ecommerce.modules.product.dto.request.ProductQuestionRequest;
import com.khahnm04.ecommerce.modules.product.dto.response.ProductQuestionResponse;

public interface ProductQuestionService {

    ProductQuestionResponse createQuestion(ProductQuestionRequest request);

}
