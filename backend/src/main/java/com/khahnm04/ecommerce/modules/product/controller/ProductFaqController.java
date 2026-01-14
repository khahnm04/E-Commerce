package com.khahnm04.ecommerce.modules.product.controller;

import com.khahnm04.ecommerce.modules.product.dto.request.ProductFaqRequest;
import com.khahnm04.ecommerce.shared.dto.ApiResponse;
import com.khahnm04.ecommerce.modules.product.dto.response.ProductFaqResponse;
import com.khahnm04.ecommerce.modules.product.service.ProductFaqService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("${api.prefix}/admin/product-faqs")
@RequiredArgsConstructor
public class ProductFaqController {

    private final ProductFaqService productFaqService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ProductFaqResponse> createProductFaq(
            @Valid @RequestBody ProductFaqRequest request
    ) {
        return ApiResponse.<ProductFaqResponse>builder()
                .data(productFaqService.createProductFaq(request))
                .message("product faq created successfully")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductFaqResponse> getDetailById(@PathVariable Long id) {
        return ApiResponse.<ProductFaqResponse>builder()
                .data(productFaqService.getDetailById(id))
                .message("get product faq detail successfully")
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<ProductFaqResponse> updateProductFaq(
            @PathVariable Long id,
            @Valid @RequestBody ProductFaqRequest request
    ) {
        return ApiResponse.<ProductFaqResponse>builder()
                .data(productFaqService.updateProductFaq(id, request))
                .message("updated product faq")
                .build();
    }

    @DeleteMapping("/{id}/soft-delete")
    public ApiResponse<Void> softDeleteProductFaq(@PathVariable Long id) {
        productFaqService.softDeleteProductFaq(id);
        return ApiResponse.<Void>builder()
                .message("soft deleted product faq")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteProductFaq(@PathVariable Long id) {
        productFaqService.deleteProductFaq(id);
        return ApiResponse.<Void>builder()
                .message("deleted product faq")
                .build();
    }

    @PatchMapping("/{id}/restore")
    public ApiResponse<Void> restoreProductFaq(@PathVariable Long id) {
        productFaqService.restoreProductFaq(id);
        return ApiResponse.<Void>builder()
                .message("product faq restored successfully")
                .build();
    }

}
