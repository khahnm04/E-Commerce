package com.khahnm04.ecommerce.modules.product.controller;

import com.khahnm04.ecommerce.modules.product.dto.request.VariantValueRequest;
import com.khahnm04.ecommerce.shared.dto.ApiResponse;
import com.khahnm04.ecommerce.shared.dto.PageResponse;
import com.khahnm04.ecommerce.modules.product.dto.response.VariantValueResponse;
import com.khahnm04.ecommerce.modules.product.service.VariantValueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("${api.prefix}/admin/variant-values")
@RequiredArgsConstructor
public class VariantValueController {

    private final VariantValueService variantValueService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<VariantValueResponse> createVariantValue(
            @Valid @RequestBody VariantValueRequest request
    ) {
        return ApiResponse.<VariantValueResponse>builder()
                .data(variantValueService.createVariantValue(request))
                .message("variant value created successfully")
                .build();
    }

    @GetMapping
    public ApiResponse<List<VariantValueResponse>> getAllVariantValues(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "displayOrder,asc") String sort
    ) {
        PageResponse<VariantValueResponse> pageResponse = variantValueService.getAllVariantValues(page - 1, size, sort);
        return ApiResponse.<List<VariantValueResponse>>builder()
                .meta(pageResponse.getMeta())
                .data(pageResponse.getData())
                .message("get all variant values successfully")
                .build();
    }

    @GetMapping("/deleted")
    public ApiResponse<List<VariantValueResponse>> getAllDeletedVariantValues(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort
    ) {
        PageResponse<VariantValueResponse> pageResponse = variantValueService.getAllDeletedVariantValues(page - 1, size, sort);
        return ApiResponse.<List<VariantValueResponse>>builder()
                .meta(pageResponse.getMeta())
                .data(pageResponse.getData())
                .message("get all soft deleted variant values successfully")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<VariantValueResponse> getVariantValueDetailById(@PathVariable Long id) {
        return ApiResponse.<VariantValueResponse>builder()
                .data(variantValueService.getDetailById(id))
                .message("get variant value detail successfully")
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<VariantValueResponse> updateVariantValue(
            @PathVariable Long id,
            @Valid @RequestBody VariantValueRequest request
    ) {
        return ApiResponse.<VariantValueResponse>builder()
                .data(variantValueService.updateVariantValue(id, request))
                .message("updated variant value")
                .build();
    }

    @DeleteMapping("/{id}/soft-delete")
    public ApiResponse<Void> softDeleteVariantValue(@PathVariable Long id) {
        variantValueService.softDeleteVariantValue(id);
        return ApiResponse.<Void>builder()
                .message("soft deleted variant value")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteVariantValue(@PathVariable Long id) {
        variantValueService.deleteVariantValue(id);
        return ApiResponse.<Void>builder()
                .message("deleted variant value")
                .build();
    }

    @PatchMapping("/{id}/restore")
    public ApiResponse<Void> restoreVariantValue(@PathVariable Long id) {
        variantValueService.restoreVariantValue(id);
        return ApiResponse.<Void>builder()
                .message("variant value restored successfully")
                .build();
    }

}
