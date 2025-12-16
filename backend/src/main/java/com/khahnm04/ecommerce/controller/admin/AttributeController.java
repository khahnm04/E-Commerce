package com.khahnm04.ecommerce.controller.admin;

import com.khahnm04.ecommerce.dto.request.product.AttributeRequest;
import com.khahnm04.ecommerce.dto.response.ApiResponse;
import com.khahnm04.ecommerce.dto.response.PageResponse;
import com.khahnm04.ecommerce.dto.response.product.AttributeResponse;
import com.khahnm04.ecommerce.service.product.AttributeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("${api.prefix}/admin/attributes")
@RequiredArgsConstructor
public class AttributeController {

    private final AttributeService attributeService;

    @PostMapping
    public ApiResponse<AttributeResponse> createAttribute(
            @Valid @RequestBody AttributeRequest request
    ) {
        return ApiResponse.<AttributeResponse>builder()
                .data(attributeService.createAttribute(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<AttributeResponse>> getAllAttributes(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort
    ) {
        PageResponse<AttributeResponse> pageResponse = attributeService.getAllAttributes(page - 1, size, sort);
        return ApiResponse.<List<AttributeResponse>>builder()
                .meta(pageResponse.getMeta())
                .data(pageResponse.getData())
                .build();
    }

    @GetMapping("/deleted")
    public ApiResponse<List<AttributeResponse>> getAllDeletedAttributes(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort
    ) {
        PageResponse<AttributeResponse> pageResponse = attributeService.getAllDeletedAttributes(page - 1, size, sort);
        return ApiResponse.<List<AttributeResponse>>builder()
                .meta(pageResponse.getMeta())
                .data(pageResponse.getData())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<AttributeResponse> getAttributeDetailById(
            @PathVariable Long id
    ) {
        return ApiResponse.<AttributeResponse>builder()
                .data(attributeService.getAttributeDetailById(id))
                .build();
    }

    @GetMapping("/code/{code}")
    public ApiResponse<AttributeResponse> getAttributeDetailByCode(
            @PathVariable String code
    ) {
        return ApiResponse.<AttributeResponse>builder()
                .data(attributeService.getAttributeDetailByCode(code))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<AttributeResponse> updateAttribute(
            @PathVariable Long id,
            @Valid @RequestBody AttributeRequest request
    ) {
        return ApiResponse.<AttributeResponse>builder()
                .data(attributeService.updateAttribute(id, request))
                .build();
    }

    @DeleteMapping("/{id}/soft-delete")
    public ApiResponse<Void> softDeleteAttribute(
            @PathVariable Long id
    ) {
        attributeService.softDeleteAttribute(id);
        return ApiResponse.<Void>builder()
                .message("soft deleted attribute")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteBanner(
            @PathVariable Long id
    ) {
        attributeService.deleteAttribute(id);
        return ApiResponse.<Void>builder()
                .message("deleted attribute")
                .build();
    }

    @PatchMapping("/{id}/restore")
    public ApiResponse<Void> restoreAttribute(@PathVariable Long id) {
        attributeService.restoreAttribute(id);
        return ApiResponse.<Void>builder()
                .message("attribute restored successfully")
                .build();
    }

}
