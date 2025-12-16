package com.khahnm04.ecommerce.controller.client;

import com.khahnm04.ecommerce.dto.request.product.ProductQuestionRequest;
import com.khahnm04.ecommerce.dto.response.ApiResponse;
import com.khahnm04.ecommerce.dto.response.product.ProductQuestionResponse;
import com.khahnm04.ecommerce.service.product.ProductQuestionService;
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
