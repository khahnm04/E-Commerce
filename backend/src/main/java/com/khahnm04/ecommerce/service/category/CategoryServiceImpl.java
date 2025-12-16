package com.khahnm04.ecommerce.service.category;

import com.khahnm04.ecommerce.common.enums.CategoryStatus;
import com.khahnm04.ecommerce.common.util.SortUtils;
import com.khahnm04.ecommerce.dto.request.category.AssignProductToCategoryRequest;
import com.khahnm04.ecommerce.dto.request.category.CategoryRequest;
import com.khahnm04.ecommerce.dto.response.category.CategoryResponse;
import com.khahnm04.ecommerce.dto.response.PageResponse;
import com.khahnm04.ecommerce.dto.response.product.ProductResponse;
import com.khahnm04.ecommerce.entity.category.Category;
import com.khahnm04.ecommerce.entity.category.ProductCategory;
import com.khahnm04.ecommerce.entity.product.Product;
import com.khahnm04.ecommerce.exception.AppException;
import com.khahnm04.ecommerce.exception.ErrorCode;
import com.khahnm04.ecommerce.mapper.CategoryMapper;
import com.khahnm04.ecommerce.mapper.ProductMapper;
import com.khahnm04.ecommerce.repository.CategoryRepository;
import com.khahnm04.ecommerce.repository.ProductCategoryRepository;
import com.khahnm04.ecommerce.repository.ProductRepository;
import com.khahnm04.ecommerce.service.upload.CloudinaryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public CategoryResponse createCategory(CategoryRequest request) {
        if (categoryRepository.existsBySlug(request.getSlug())) {
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }
        if (categoryRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }

        Category category = categoryMapper.fromCategoryRequestToCategory(request);
        category.setImage(cloudinaryService.upload(request.getImage()));

        category.setParent(Optional.ofNullable(request.getParentId())
                .map(this::getCategoryById)
                .orElse(null));

        String path = Optional.ofNullable(category.getParent())
                .map(Category::getPath)
                .orElse("");

        Category savedCategory = categoryRepository.save(category);
        savedCategory.setPath(path + savedCategory.getId() + "/");
        savedCategory = categoryRepository.save(savedCategory);

        log.info("Saved new category with id {}", savedCategory.getId());
        return categoryMapper.toCategoryResponse(savedCategory);
    }

    @Override
    public PageResponse<CategoryResponse> getAllCategories(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, SortUtils.parseSort(sort));
        Page<Category> categoryPage = categoryRepository.findAllByDeletedAtIsNull(pageable);
        Page<CategoryResponse> dtoPage = categoryPage.map(categoryMapper::toCategoryResponse);
        return PageResponse.fromPage(dtoPage);
    }

    @Override
    public PageResponse<CategoryResponse> getAllDeletedCategories(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, SortUtils.parseSort(sort));
        Page<Category> categoryPage = categoryRepository.findAllByDeletedAtIsNotNull(pageable);
        Page<CategoryResponse> dtoPage = categoryPage.map(categoryMapper::toCategoryResponse);
        return PageResponse.fromPage(dtoPage);
    }

    @Override
    public CategoryResponse getCategoryDetailById(Long id) {
        return categoryMapper.toCategoryResponse(getCategoryById(id));
    }

    @Override
    public CategoryResponse getCategoryDetailBySlug(String slug) {
        Category category = categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(Long id, CategoryRequest request, MultipartFile file) {
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
        category.setImage(cloudinaryService.upload(file));

        Long currentParentId = (category.getParent() != null) ? category.getParent().getId() : null;
        Long newParentId = request.getParentId();

        if (Objects.equals(currentParentId, newParentId)) {
            Category savedCategory = categoryRepository.save(category);
            log.info("Updated category (no parent change) with id {}", savedCategory.getId());
            return categoryMapper.toCategoryResponse(savedCategory);
        } else {
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

            log.info("Updated category and moved tree with id {}", savedCategory.getId());
            return categoryMapper.toCategoryResponse(savedCategory);
        }
    }

    @Override
    @Transactional
    public void updateCategoryStatus(Long id, String status) {
        Category category = getCategoryById(id);

        boolean isValid = Arrays.stream(CategoryStatus.values())
                .anyMatch(e -> e.name().equalsIgnoreCase(status));
        if (!isValid) {
            throw new AppException(ErrorCode.INVALID_ENUM_VALUE);
        }

        String pathWithWildcard = category.getPath() + "%";

        if (CategoryStatus.INACTIVE.name().equalsIgnoreCase(status)) {
            category.setStatus(CategoryStatus.INACTIVE_MANUAL);
            categoryRepository.save(category);
            categoryRepository.updateDescendantStatusesByPath(
                    pathWithWildcard, category.getId(), CategoryStatus.INACTIVE_CASCADE, CategoryStatus.ACTIVE);
        } else if (CategoryStatus.ACTIVE.name().equalsIgnoreCase(status)) {
            category.setStatus(CategoryStatus.ACTIVE);
            categoryRepository.save(category);
            categoryRepository.updateDescendantStatusesByPath(
                    pathWithWildcard, category.getId(), CategoryStatus.ACTIVE, CategoryStatus.INACTIVE_CASCADE);
        } else {
            throw new RuntimeException("status action is invalid!");
        }
    }

    @Override
    public void softDeleteCategory(Long id) {
        Category category = getCategoryById(id);

        if (categoryRepository.existsByParent(id)) {
            throw new AppException(ErrorCode.CATEGORY_HAS_CHILDREN);
        }

        category.setDeletedAt(LocalDateTime.now());
        categoryRepository.save(category);
        log.info("Category with id {} has been soft deleted", id);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = getCategoryById(id);
        categoryRepository.delete(category);
        log.info("Category with id {} has been deleted", id);
    }

    @Override
    public void restoreCategory(Long id) {
        Category category = getCategoryById(id);
        category.setDeletedAt(null);
        categoryRepository.save(category);
        log.info("Category restored with id {}", id);
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
        log.info("Completed assigning {} products to category {}", productIds.size(), id);
    }


    @Override
    public PageResponse<ProductResponse> getAllProductsByCategoryId(int page, int size, String sort, Long id) {
        getCategoryById(id);
        Pageable pageable = PageRequest.of(page, size, SortUtils.parseSort(sort));
        Page<Product> productPage = productCategoryRepository.findAllByCategoryId(id, pageable);
        Page<ProductResponse> dtoPage = productPage.map(productMapper::fromProductToProductResponse);
        return PageResponse.fromPage(dtoPage);
    }

    private Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
    }

}
