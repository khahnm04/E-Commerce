package com.khahnm04.ecommerce.modules.category.service;

import com.khahnm04.ecommerce.modules.category.dto.request.*;
import com.khahnm04.ecommerce.modules.category.repository.specification.CategorySpecification;
import com.khahnm04.ecommerce.shared.enums.CategoryStatus;
import com.khahnm04.ecommerce.shared.util.SortUtils;
import com.khahnm04.ecommerce.modules.category.dto.response.CategoryResponse;
import com.khahnm04.ecommerce.shared.dto.PageResponse;
import com.khahnm04.ecommerce.modules.product.dto.response.ProductResponse;
import com.khahnm04.ecommerce.modules.category.entity.Category;
import com.khahnm04.ecommerce.modules.product.entity.ProductCategory;
import com.khahnm04.ecommerce.modules.product.entity.Product;
import com.khahnm04.ecommerce.core.exception.AppException;
import com.khahnm04.ecommerce.core.exception.ErrorCode;
import com.khahnm04.ecommerce.modules.category.mapper.CategoryMapper;
import com.khahnm04.ecommerce.modules.product.mapper.ProductMapper;
import com.khahnm04.ecommerce.modules.category.repository.CategoryRepository;
import com.khahnm04.ecommerce.modules.product.repository.ProductCategoryRepository;
import com.khahnm04.ecommerce.modules.product.repository.ProductRepository;
import com.khahnm04.ecommerce.modules.storage.service.CloudinaryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final ProductCategoryRepository productCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CloudinaryService cloudinaryService;
    private final CategoryMapper categoryMapper;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public CategoryResponse createCategory(CategoryRequest request, MultipartFile image) {
        if (categoryRepository.existsBySlug(request.getSlug())) {
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }
        if (categoryRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }

        Category category = categoryMapper.fromCategoryRequestToCategory(request);
        category.setImage(cloudinaryService.upload(image));

        category.setParent(Optional.ofNullable(request.getParentId())
                .map(this::getCategoryById)
                .orElse(null));

        String parentPath = Optional.ofNullable(category.getParent())
                .map(Category::getPath)
                .orElse("");

        Category savedCategory = categoryRepository.save(category);
        savedCategory.setPath(parentPath + savedCategory.getId() + "/");
        savedCategory = categoryRepository.save(savedCategory);

        log.info("Created category id: {}", savedCategory.getId());
        return categoryMapper.toCategoryResponse(savedCategory);
    }

    @Override
    public PageResponse<CategoryResponse> getAllCategories(CategoryFilterRequest filter) {
        Pageable pageable = PageRequest.of(filter.getPage() - 1, filter.getSize(), SortUtils.parseSort(filter.getSort()));

        Specification<Category> spec = CategorySpecification.notDeleted()
                .and(CategorySpecification.hasParent(filter.getParentId()))
                .and(CategorySpecification.hasStatus(filter.getStatus()))
                .and(CategorySpecification.searchByName(filter.getSearch()))
                .and(CategorySpecification.hasTimestampBetween(filter.getDateType(), filter.getStartDate(), filter.getEndDate()));

        Page<Category> categoryPage = categoryRepository.findAll(spec, pageable);
        return PageResponse.fromPage(categoryPage.map(categoryMapper::toCategoryResponse));
    }

    @Override
    public PageResponse<CategoryResponse> getAllDeletedCategories(CategoryFilterRequest filter) {
        Pageable pageable = PageRequest.of(filter.getPage() - 1, filter.getSize(), SortUtils.parseSort(filter.getSort()));

        Specification<Category> spec = CategorySpecification.isDeleted()
                .and(CategorySpecification.hasParent(filter.getParentId()))
                .and(CategorySpecification.searchByName(filter.getSearch()))
                .and(CategorySpecification.hasTimestampBetween(filter.getDateType(), filter.getStartDate(), filter.getEndDate()));

        Page<Category> categoryPage = categoryRepository.findAll(spec, pageable);
        return PageResponse.fromPage(categoryPage.map(categoryMapper::toCategoryResponse));
    }

    @Override
    public CategoryResponse getCategoryDetailById(Long id) {
        return categoryMapper.toCategoryResponse(getCategoryById(id));
    }

    @Override
    public CategoryResponse getCategoryDetailBySlug(String slug) {
        return categoryMapper.toCategoryResponse(getCategoryBySlug(slug));
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(Long id, CategoryRequest request, MultipartFile image) {
        Category category = getCategoryById(id);

        if (StringUtils.hasText(request.getName())
                && categoryRepository.existsByNameIgnoreCaseAndIdNot(request.getName(), id)) {
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }
        if (StringUtils.hasText(request.getSlug())
                && categoryRepository.existsBySlugIgnoreCaseAndIdNot(request.getSlug(), id)) {
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }

        categoryMapper.updateCategory(category, request);
        if (image != null && !image.isEmpty()) {
            category.setImage(cloudinaryService.upload(image));
        }

        Long currentParentId = (category.getParent() != null) ? category.getParent().getId() : null;
        Long newParentId = request.getParentId();

        if (Objects.equals(currentParentId, newParentId)) {
            return categoryMapper.toCategoryResponse(categoryRepository.save(category));
        }

        String oldPath = category.getPath();
        String newPath = category.getId() + "/";
        Category newParent = null;

        if (newParentId != null) {
            newParent = getCategoryById(newParentId);
            if (newParent.getPath().startsWith(oldPath)) {
                throw new AppException(ErrorCode.CANNOT_MOVE_PARENT_TO_CHILD);
            }
            newPath = newParent.getPath() + newPath;
        }

        category.setParent(newParent);
        category.setPath(newPath);

        Category savedCategory = categoryRepository.save(category);
        categoryRepository.updateDescendantPaths(oldPath + "%", newPath, oldPath.length());
        log.info("Updated category tree for id {}", savedCategory.getId());
        return categoryMapper.toCategoryResponse(savedCategory);
    }

    @Override
    @Transactional
    public void updateCategoryStatus(Long id, UpdateStatusRequest request) {
        Category category = getCategoryById(id);
        CategoryStatus newStatus;
        try {
            newStatus = CategoryStatus.valueOf(request.getStatus().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AppException(ErrorCode.INVALID_ENUM_VALUE);
        }
        if (category.getStatus() == newStatus) {
            return;
        }
        category.setStatus(newStatus);
        categoryRepository.save(category);
        log.info("Changed status for category id {} from {} to {}", id, category.getStatus(), newStatus);
    }

    @Override
    @Transactional
    public void softDeleteCategory(Long id) {
        Category category = getCategoryById(id);

        List<Long> allRelatedIds = categoryRepository.findAllIdsByPath(category.getPath());

        boolean hasProducts = productCategoryRepository.existsByCategoryIdIn(allRelatedIds);
        if (hasProducts) {
            throw new AppException(ErrorCode.CATEGORY_HAS_PRODUCTS);
        }

        categoryRepository.softDeleteAllByIds(allRelatedIds, LocalDateTime.now());
        log.info("Soft deleted category id {} and {} descendants", id, allRelatedIds.size() - 1);
    }

    @Override
    @Transactional
    public void restoreCategory(Long id) {
        Category category = getCategoryById(id);

        if (category.getDeletedAt() == null) {
            return;
        }

        LocalDateTime deletedTime = category.getDeletedAt();
        LocalDateTime start = deletedTime.minusSeconds(2);
        LocalDateTime end = deletedTime.plusSeconds(2);

        categoryRepository.restoreDescendantsByTimeRange(category.getPath(), start, end);
        log.info("Restored category id {} and matching descendants", id);
    }

    @Override
    @Transactional
    public void assignProductToCategory(Long id, AssignProductToCategoryRequest request) {
        Category category = getCategoryById(id);
        Set<Long> productIds = request.getProductIds();

        Set<Long> existing = productCategoryRepository
                .findExistingProductIds(category.getId(), productIds);
        if (!existing.isEmpty()) {
            throw new AppException(ErrorCode.DUPLICATE_CATEGORY_PRODUCT);
        }

        List<Product> products = productRepository.findAllById(productIds);
        if (products.size() != productIds.size()) {
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        List<ProductCategory> newRelations = products.stream()
                .map(p -> ProductCategory.builder()
                        .category(category)
                        .product(p)
                        .build())
                .toList();

        productCategoryRepository.saveAll(newRelations);
        log.info("Assigned {} products to category {}", productIds.size(), id);
    }

    @Override
    public PageResponse<ProductResponse> getAllProductsByCategoryId(int page, int size, String sort, Long id) {
        getCategoryById(id);
        Pageable pageable = PageRequest.of(page, size, SortUtils.parseSort(sort));
        Page<Product> productPage = productCategoryRepository.findAllByCategoryId(id, pageable);
        Page<ProductResponse> dtoPage = productPage.map(productMapper::fromProductToProductResponse);
        return PageResponse.fromPage(dtoPage);
    }

    @Override
    @Transactional
    public void handleBulkAction(CategoryBulkActionRequest request) {
        Set<Long> categoryIds = request.getCategoryIds();
        if (categoryIds == null || categoryIds.isEmpty()) {
            throw new AppException(ErrorCode.BULK_ACTION_REQUIRED_IDS);
        }
        String action = request.getAction().toUpperCase();
        switch (action) {
            case "ACTIVE"   -> categoryRepository.updateStatusByIds(CategoryStatus.ACTIVE, categoryIds);
            case "INACTIVE" -> categoryRepository.updateStatusByIds(CategoryStatus.INACTIVE, categoryIds);
            case "DELETE"   -> categoryRepository.softDeleteByIds(LocalDateTime.now(), categoryIds);
            case "RESTORE"  ->  categoryRepository.restoreDeleteByIds(categoryIds);
            default         -> throw new AppException(ErrorCode.INVALID_BULK_ACTION);
        }
    }

    private Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
    }

    private Category getCategoryBySlug(String slug) {
        return categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
    }

}
