package com.khahnm04.ecommerce.controller.admin;

import com.khahnm04.ecommerce.dto.request.product.ProductVariantRequest;
import com.khahnm04.ecommerce.dto.response.ApiResponse;
import com.khahnm04.ecommerce.dto.response.PageResponse;
import com.khahnm04.ecommerce.dto.response.product.ProductVariantResponse;
import com.khahnm04.ecommerce.service.product.ProductVariantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("${api.prefix}/admin/product-variants")
@RequiredArgsConstructor
public class ProductVariantController {

    private final ProductVariantService productVariantService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ProductVariantResponse> createProductVariant(
            @Valid @ModelAttribute ProductVariantRequest request
    ) {
        return ApiResponse.<ProductVariantResponse>builder()
                .data(productVariantService.createProductVariant(request))
                .message("product variant created successfully")
                .build();
    }

    @GetMapping
    public ApiResponse<List<ProductVariantResponse>> getAllProductVariants(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort
    ) {
        PageResponse<ProductVariantResponse> pageResponse = productVariantService.getAllProductVariants(page - 1, size, sort);
        return ApiResponse.<List<ProductVariantResponse>>builder()
                .meta(pageResponse.getMeta())
                .data(pageResponse.getData())
                .message("get all product variants successfully")
                .build();
    }

    @GetMapping("/deleted")
    public ApiResponse<List<ProductVariantResponse>> getAllDeletedProductVariants(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort
    ) {
        PageResponse<ProductVariantResponse> pageResponse = productVariantService.getAllDeletedProductVariants(page - 1, size, sort);
        return ApiResponse.<List<ProductVariantResponse>>builder()
                .meta(pageResponse.getMeta())
                .data(pageResponse.getData())
                .message("get all soft deleted product variants successfully")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductVariantResponse> getDetailById(@PathVariable Long id) {
        return ApiResponse.<ProductVariantResponse>builder()
                .data(productVariantService.getDetailById(id))
                .message("get product variant detail successfully")
                .build();
    }

    @GetMapping("/sku/{sku}")
    public ApiResponse<ProductVariantResponse> getDetailBySku(@PathVariable String sku) {
        return ApiResponse.<ProductVariantResponse>builder()
                .data(productVariantService.getDetailBySku(sku))
                .message("get product variant detail successfully")
                .build();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ProductVariantResponse> updateProductVariant(
            @PathVariable Long id,
            @Valid @ModelAttribute ProductVariantRequest request,
            @RequestPart(value = "image", required = false) MultipartFile file
    ) {
        return ApiResponse.<ProductVariantResponse>builder()
                .data(productVariantService.updateProductVariant(id, request, file))
                .message("updated product variant")
                .build();
    }

    @DeleteMapping("/{id}/soft-delete")
    public ApiResponse<Void> softDeleteProductVariant(@PathVariable Long id) {
        productVariantService.softDeleteProductVariant(id);
        return ApiResponse.<Void>builder()
                .message("soft deleted product variant")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteProductVariant(@PathVariable Long id) {
        productVariantService.deleteProductVariant(id);
        return ApiResponse.<Void>builder()
                .message("deleted product variant")
                .build();
    }

    @PatchMapping("/{id}/restore")
    public ApiResponse<Void> restoreProductVariant(@PathVariable Long id) {
        productVariantService.restoreProductVariant(id);
        return ApiResponse.<Void>builder()
                .message("product variant restored successfully")
                .build();
    }

}
