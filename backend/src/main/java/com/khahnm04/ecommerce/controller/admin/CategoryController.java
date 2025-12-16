package com.khahnm04.ecommerce.controller.admin;

import com.khahnm04.ecommerce.dto.request.category.AssignProductToCategoryRequest;
import com.khahnm04.ecommerce.dto.request.category.CategoryRequest;
import com.khahnm04.ecommerce.dto.response.ApiResponse;
import com.khahnm04.ecommerce.dto.response.category.CategoryResponse;
import com.khahnm04.ecommerce.dto.response.PageResponse;
import com.khahnm04.ecommerce.dto.response.product.ProductResponse;
import com.khahnm04.ecommerce.service.category.CategoryService;
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
@RequestMapping("${api.prefix}/admin/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CategoryResponse> createCategory(
            @Valid @ModelAttribute CategoryRequest request
    ) {
        return ApiResponse.<CategoryResponse>builder()
                .data(categoryService.createCategory(request))
                .message("category created successfully")
                .build();
    }

    @GetMapping
    public ApiResponse<List<CategoryResponse>> getAllCategories(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort
    ) {
        PageResponse<CategoryResponse> pageResponse = categoryService.getAllCategories(page - 1, size, sort);
        return ApiResponse.<List<CategoryResponse>>builder()
                .data(pageResponse.getData())
                .meta(pageResponse.getMeta())
                .message("get all categories successfully")
                .build();
    }

    @GetMapping("/deleted")
    public ApiResponse<List<CategoryResponse>> getAllDeletedCategories(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort
    ) {
        PageResponse<CategoryResponse> pageResponse = categoryService.getAllDeletedCategories(page - 1, size, sort);
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
                .message("get category detail by id successfully")
                .build();
    }

    @GetMapping("/slug/{slug}")
    public ApiResponse<CategoryResponse> getCategoryDetailBySlug(
            @PathVariable String slug
    ) {
        return ApiResponse.<CategoryResponse>builder()
                .data(categoryService.getCategoryDetailBySlug(slug))
                .message("get category detail by id successfully")
                .build();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<CategoryResponse> updateCategory(
            @PathVariable Long id,
            @Valid CategoryRequest request,
            @RequestPart(value = "image", required = false) MultipartFile file
    ) {
        return ApiResponse.<CategoryResponse>builder()
                .data(categoryService.updateCategory(id, request, file))
                .message("updated category")
                .build();
    }

    @PatchMapping("/{id}/status")
    public ApiResponse<Void> updateCategoryStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        categoryService.updateCategoryStatus(id, status);
        return ApiResponse.<Void>builder()
                .message("category status updated successfully")
                .build();
    }

    @DeleteMapping("/{id}/soft-delete")
    public ApiResponse<Void> softDeleteCategory(
            @PathVariable Long id
    ) {
        categoryService.softDeleteCategory(id);
        return ApiResponse.<Void>builder()
                .message("soft deleted category")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteCategory(
            @PathVariable Long id
    ) {
        categoryService.deleteCategory(id);
        return ApiResponse.<Void>builder()
                .message("deleted category")
                .build();
    }

    @PatchMapping("/{id}/restore")
    public ApiResponse<Void> restoreCategory(
            @PathVariable Long id
    ) {
        categoryService.restoreCategory(id);
        return ApiResponse.<Void>builder()
                .message("category restored successfully")
                .build();
    }

    @PostMapping("/{id}/products")
    public ApiResponse<Void> assignProductToCategory(
            @PathVariable Long id,
            @Valid @RequestBody AssignProductToCategoryRequest request
    ) {
        categoryService.assignProductToCategory(id, request);
        return ApiResponse.<Void>builder()
                .message("assign product to category successfully")
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

}
