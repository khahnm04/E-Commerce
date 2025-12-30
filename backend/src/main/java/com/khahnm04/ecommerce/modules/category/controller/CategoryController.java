package com.khahnm04.ecommerce.modules.category.controller;

import com.khahnm04.ecommerce.core.exception.ServiceValidationException;
import com.khahnm04.ecommerce.modules.category.dto.request.*;
import com.khahnm04.ecommerce.shared.dto.ApiResponse;
import com.khahnm04.ecommerce.modules.category.dto.response.CategoryResponse;
import com.khahnm04.ecommerce.shared.dto.PageResponse;
import com.khahnm04.ecommerce.modules.product.dto.response.ProductResponse;
import com.khahnm04.ecommerce.modules.category.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("${api.prefix}/admin/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CategoryResponse> createCategory(
            @Valid @RequestPart("data") CategoryRequest request,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        if (image == null || image.isEmpty()) {
            throw new ServiceValidationException("image", "image cannot be null");
        }
        return ApiResponse.<CategoryResponse>builder()
                .data(categoryService.createCategory(request, image))
                .message("category created successfully")
                .build();
    }

    @GetMapping
    public ApiResponse<List<CategoryResponse>> getAllCategories(
            @ModelAttribute CategoryFilterRequest filter
    ) {
        PageResponse<CategoryResponse> pageResponse = categoryService.getAllCategories(filter);
        return ApiResponse.<List<CategoryResponse>>builder()
                .data(pageResponse.getData())
                .meta(pageResponse.getMeta())
                .message("get all categories successfully")
                .build();
    }

    @GetMapping("/deleted")
    public ApiResponse<List<CategoryResponse>> getAllDeletedCategories(
            @ModelAttribute CategoryFilterRequest filter
    ) {
        PageResponse<CategoryResponse> pageResponse = categoryService.getAllDeletedCategories(filter);
        return ApiResponse.<List<CategoryResponse>>builder()
                .data(pageResponse.getData())
                .meta(pageResponse.getMeta())
                .message("get all soft deleted categories successfully")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<CategoryResponse> getCategoryDetailById(
            @PathVariable Long id
    ) {
        return ApiResponse.<CategoryResponse>builder()
                .data(categoryService.getCategoryDetailById(id))
                .message("get request detail by id successfully")
                .build();
    }

    @GetMapping("/slug/{slug}")
    public ApiResponse<CategoryResponse> getCategoryDetailBySlug(
            @PathVariable String slug
    ) {
        return ApiResponse.<CategoryResponse>builder()
                .data(categoryService.getCategoryDetailBySlug(slug))
                .message("get request detail by id successfully")
                .build();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<CategoryResponse> updateCategory(
            @PathVariable Long id,
            @Valid @RequestPart("data") CategoryRequest request,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        return ApiResponse.<CategoryResponse>builder()
                .data(categoryService.updateCategory(id, request, image))
                .message("updated request")
                .build();
    }

    @PatchMapping("/{id}/status")
    public ApiResponse<Void> updateCategoryStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateStatusRequest status
    ) {
        categoryService.updateCategoryStatus(id, status);
        return ApiResponse.<Void>builder()
                .message("request status updated successfully")
                .build();
    }

    @DeleteMapping("/{id}/soft-delete")
    public ApiResponse<Void> softDeleteCategory(
            @PathVariable Long id
    ) {
        categoryService.softDeleteCategory(id);
        return ApiResponse.<Void>builder()
                .message("soft deleted request")
                .build();
    }

    @PatchMapping("/{id}/restore")
    public ApiResponse<Void> restoreCategory(
            @PathVariable Long id
    ) {
        categoryService.restoreCategory(id);
        return ApiResponse.<Void>builder()
                .message("request restored successfully")
                .build();
    }

    @PostMapping("/{id}/products")
    public ApiResponse<Void> assignProductToCategory(
            @PathVariable Long id,
            @Valid @RequestBody AssignProductToCategoryRequest request
    ) {
        categoryService.assignProductToCategory(id, request);
        return ApiResponse.<Void>builder()
                .message("assign product to request successfully")
                .build();
    }

    @GetMapping("/{id}/products")
    public ApiResponse<List<ProductResponse>> getAllProductsByCategoryId(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort,
            @PathVariable Long id
    ) {
        PageResponse<ProductResponse> pageResponse = categoryService.getAllProductsByCategoryId(page - 1, size, sort, id);
        return ApiResponse.<List<ProductResponse>>builder()
                .meta(pageResponse.getMeta())
                .data(pageResponse.getData())
                .build();
    }

    @PatchMapping("/bulk")
    public ApiResponse<Void> bulkAction(
            @Valid @RequestBody CategoryBulkActionRequest request
    ) {
        categoryService.handleBulkAction(request);
        return ApiResponse.<Void>builder()
                .message("Successfully execute a series of actions.")
                .build();
    }

}
