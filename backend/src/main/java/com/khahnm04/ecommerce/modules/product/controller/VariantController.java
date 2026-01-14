package com.khahnm04.ecommerce.modules.product.controller;

import com.khahnm04.ecommerce.modules.product.dto.request.VariantRequest;
import com.khahnm04.ecommerce.shared.dto.ApiResponse;
import com.khahnm04.ecommerce.shared.dto.PageResponse;
import com.khahnm04.ecommerce.modules.product.dto.response.VariantResponse;
import com.khahnm04.ecommerce.modules.product.dto.response.VariantValueResponse;
import com.khahnm04.ecommerce.modules.product.service.VariantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("${api.prefix}/admin/variants")
@RequiredArgsConstructor
public class VariantController {

    private final VariantService variantService;

    @PostMapping
    public ApiResponse<VariantResponse> createVariant(
            @Valid @RequestBody VariantRequest request
    ) {
        return ApiResponse.<VariantResponse>builder()
                .data(variantService.createVariant(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<VariantResponse>> getAllVariants(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort
    ) {
        PageResponse<VariantResponse> pageResponse = variantService.getAllVariants(page - 1, size, sort);
        return ApiResponse.<List<VariantResponse>>builder()
                .meta(pageResponse.getMeta())
                .data(pageResponse.getData())
                .build();
    }

    @GetMapping("/deleted")
    public ApiResponse<List<VariantResponse>> getAllDeletedVariants(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort
    ) {
        PageResponse<VariantResponse> pageResponse = variantService.getAllDeletedVariants(page - 1, size, sort);
        return ApiResponse.<List<VariantResponse>>builder()
                .meta(pageResponse.getMeta())
                .data(pageResponse.getData())
                .build();
    }

    @GetMapping("/{variantId}/variant-values")
    public ApiResponse<List<VariantValueResponse>> getVariantValuesByVariantId(
            @PathVariable Long variantId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "displayOrder,asc") String sort
    ) {
        PageResponse<VariantValueResponse> pageResponse = variantService.getVariantValuesByVariantId(variantId, page - 1, size, sort);
        return ApiResponse.<List<VariantValueResponse>>builder()
                .meta(pageResponse.getMeta())
                .data(pageResponse.getData())
                .message("get variant values by variant id successfully")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<VariantResponse> getVariantDetailById(
            @PathVariable Long id
    ) {
        return ApiResponse.<VariantResponse>builder()
                .data(variantService.getVariantDetailById(id))
                .build();
    }

    @GetMapping("/code/{code}")
    public ApiResponse<VariantResponse> getVariantDetailByCode(
            @PathVariable String code
    ) {
        return ApiResponse.<VariantResponse>builder()
                .data(variantService.getVariantDetailByCode(code))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<VariantResponse> updateVariant(
            @PathVariable Long id,
            @Valid @RequestBody VariantRequest request
    ) {
        return ApiResponse.<VariantResponse>builder()
                .data(variantService.updateVariant(id, request))
                .build();
    }

    @DeleteMapping("/{id}/soft-delete")
    public ApiResponse<Void> softDeleteVariant(
            @PathVariable Long id
    ) {
        variantService.softDeleteVariant(id);
        return ApiResponse.<Void>builder()
                .message("soft deleted variant")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteVariant(
            @PathVariable Long id
    ) {
        variantService.deleteVariant(id);
        return ApiResponse.<Void>builder()
                .message("deleted variant")
                .build();
    }

    @PatchMapping("/{id}/restore")
    public ApiResponse<Void> restoreVariant(@PathVariable Long id) {
        variantService.restoreVariant(id);
        return ApiResponse.<Void>builder()
                .message("variant restored successfully")
                .build();
    }

}
