package com.khahnm04.ecommerce.modules.product.controller;

import com.khahnm04.ecommerce.modules.product.dto.request.ProductQuestionRequest;
import com.khahnm04.ecommerce.shared.dto.ApiResponse;
import com.khahnm04.ecommerce.modules.product.dto.response.ProductQuestionResponse;
import com.khahnm04.ecommerce.modules.product.service.ProductQuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/product-questions")
@RequiredArgsConstructor
public class ProductQuestionController {

    private final ProductQuestionService questionService;

    @PostMapping
    public ApiResponse<ProductQuestionResponse> createQuestion(
            @Valid @RequestBody ProductQuestionRequest request
    ) {
        return ApiResponse.<ProductQuestionResponse>builder()
                .data(questionService.createQuestion(request))
                .message("Question submitted successfully")
                .build();
    }

}
