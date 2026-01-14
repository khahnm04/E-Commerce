package com.khahnm04.ecommerce.modules.category.service;

import com.khahnm04.ecommerce.modules.category.dto.request.*;
import com.khahnm04.ecommerce.modules.category.dto.response.CategoryResponse;
import com.khahnm04.ecommerce.shared.dto.PageResponse;
import com.khahnm04.ecommerce.modules.product.dto.response.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest request, MultipartFile image);
    PageResponse<CategoryResponse> getAllCategories(CategoryFilterRequest filter);
    PageResponse<CategoryResponse> getAllDeletedCategories(CategoryFilterRequest filter);
    CategoryResponse getCategoryDetailById(Long id);
    CategoryResponse getCategoryDetailBySlug(String slug);
    CategoryResponse updateCategory(Long id, CategoryRequest request, MultipartFile image);
    void updateCategoryStatus(Long id, UpdateStatusRequest status);
    void softDeleteCategory(Long id);
    void restoreCategory(Long id);

    void assignProductToCategory(Long id, AssignProductToCategoryRequest request);
    PageResponse<ProductResponse> getAllProductsByCategoryId(int page, int size, String sort, Long categoryId);

    void handleBulkAction(CategoryBulkActionRequest request);

}
