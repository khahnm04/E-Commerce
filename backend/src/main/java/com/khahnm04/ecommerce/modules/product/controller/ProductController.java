package com.khahnm04.ecommerce.modules.product.controller;

import com.khahnm04.ecommerce.modules.product.dto.request.ProductRequest;
import com.khahnm04.ecommerce.shared.dto.ApiResponse;
import com.khahnm04.ecommerce.modules.product.dto.response.ProductFaqResponse;
import com.khahnm04.ecommerce.modules.product.dto.response.ProductQuestionResponse;
import com.khahnm04.ecommerce.modules.product.dto.response.ProductResponse;
import com.khahnm04.ecommerce.shared.dto.PageResponse;
import com.khahnm04.ecommerce.modules.product.dto.response.ProductVariantResponse;
import com.khahnm04.ecommerce.core.exception.ServiceValidationException;
import com.khahnm04.ecommerce.modules.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("${api.prefix}/admin/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ProductResponse> createProduct(
            @Valid @RequestPart("data") ProductRequest request,
            @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail
    ) {
        if (thumbnail == null || thumbnail.isEmpty()) {
            throw new ServiceValidationException("thumbnail", "thumbnail cannot be null");
        }
        return ApiResponse.<ProductResponse>builder()
                .data(productService.createProduct(request, thumbnail))
                .message("product created successfully")
                .build();
    }

    @GetMapping
    public ApiResponse<List<ProductResponse>> getAllProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort
    ) {
        PageResponse<ProductResponse> pageResponse = productService.getAllProducts(page - 1, size, sort);
        return ApiResponse.<List<ProductResponse>>builder()
                .data(pageResponse.getData())
                .meta(pageResponse.getMeta())
                .message("get all products successfully")
                .build();
    }

    @GetMapping("/deleted")
    public ApiResponse<List<ProductResponse>> getAllDeletedProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort
    ) {
        PageResponse<ProductResponse> pageResponse = productService.getAllDeletedProducts(page - 1, size, sort);
        return ApiResponse.<List<ProductResponse>>builder()
                .data(pageResponse.getData())
                .meta(pageResponse.getMeta())
                .message("get all soft deleted products successfully")
                .build();
    }

    @GetMapping("/{productId}/product-variants")
    public ApiResponse<List<ProductVariantResponse>> getProductVariantsByProductId(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort
    ) {
        PageResponse<ProductVariantResponse> pageResponse = productService.getProductVariantsByProductId(productId, page - 1, size, sort);
        return ApiResponse.<List<ProductVariantResponse>>builder()
                .meta(pageResponse.getMeta())
                .data(pageResponse.getData())
                .message("get variants by product id successfully")
                .build();
    }

    @GetMapping("/{productId}/product-faqs")
    public ApiResponse<List<ProductFaqResponse>> getFaqsByProductId(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "displayOrder,asc") String sort
    ) {
        PageResponse<ProductFaqResponse> pageResponse = productService.getFaqsByProductId(productId, page - 1, size, sort);
        return ApiResponse.<List<ProductFaqResponse>>builder()
                .meta(pageResponse.getMeta())
                .data(pageResponse.getData())
                .message("get product faqs successfully")
                .build();
    }


    @GetMapping("/{productId}/product-questions")
    public ApiResponse<List<ProductQuestionResponse>> getQuestionsByProduct(
            @PathVariable Long productId
    ) {
        return ApiResponse.<List<ProductQuestionResponse>>builder()
                .data(productService.getQuestionsByProductId(productId))
                .message("Get product questions successfully")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getProductDetailById(@PathVariable Long id) {
        return ApiResponse.<ProductResponse>builder()
                .data(productService.getProductDetailById(id))
                .message("get product detail successfully")
                .build();
    }

    @GetMapping("/slug/{slug}")
    public ApiResponse<ProductResponse> getProductDetailBySlug(@PathVariable String slug) {
        return ApiResponse.<ProductResponse>builder()
                .data(productService.getProductDetailBySlug(slug))
                .message("get product detail successfully")
                .build();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ProductResponse> updateProduct(
            @PathVariable Long id,
            @Valid @RequestPart("data") ProductRequest request,
            @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail
    ) {
        if (thumbnail == null || thumbnail.isEmpty()) {
            throw new ServiceValidationException("thumbnail", "thumbnail cannot be null");
        }
        return ApiResponse.<ProductResponse>builder()
                .data(productService.updateProduct(id, request, thumbnail))
                .message("updated product")
                .build();
    }

    @PatchMapping("/{id}/status")
    public ApiResponse<Void> updateProductStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        productService.updateProductStatus(id, status);
        return ApiResponse.<Void>builder()
                .message("product status updated successfully")
                .build();
    }

    @DeleteMapping("/{id}/soft-delete")
    public ApiResponse<Void> softDeleteProduct(@PathVariable Long id) {
        productService.softDeleteProduct(id);
        return ApiResponse.<Void>builder()
                .message("soft deleted product")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ApiResponse.<Void>builder()
                .message("deleted product")
                .build();
    }

    @PatchMapping("/{id}/restore")
    public ApiResponse<Void> restoreProduct(@PathVariable Long id) {
        productService.restoreProduct(id);
        return ApiResponse.<Void>builder()
                .message("product restored successfully")
                .build();
    }

}
