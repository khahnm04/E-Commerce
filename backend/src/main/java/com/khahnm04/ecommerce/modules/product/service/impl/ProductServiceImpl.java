package com.khahnm04.ecommerce.modules.product.service.impl;

import com.khahnm04.ecommerce.modules.brand.repository.BrandRepository;
import com.khahnm04.ecommerce.modules.category.repository.CategoryRepository;
import com.khahnm04.ecommerce.modules.product.entity.*;
import com.khahnm04.ecommerce.modules.product.repository.*;
import com.khahnm04.ecommerce.shared.enums.ProductStatus;
import com.khahnm04.ecommerce.shared.util.SortUtils;
import com.khahnm04.ecommerce.modules.product.dto.request.ProductRequest;
import com.khahnm04.ecommerce.modules.product.dto.response.ProductFaqResponse;
import com.khahnm04.ecommerce.modules.product.dto.response.ProductQuestionResponse;
import com.khahnm04.ecommerce.modules.product.dto.response.ProductResponse;
import com.khahnm04.ecommerce.shared.dto.PageResponse;
import com.khahnm04.ecommerce.modules.product.dto.response.ProductVariantResponse;
import com.khahnm04.ecommerce.modules.brand.entity.Brand;
import com.khahnm04.ecommerce.modules.category.entity.Category;
import com.khahnm04.ecommerce.modules.product.entity.ProductCategory;
import com.khahnm04.ecommerce.core.exception.AppException;
import com.khahnm04.ecommerce.core.exception.ErrorCode;
import com.khahnm04.ecommerce.modules.product.mapper.ProductFaqMapper;
import com.khahnm04.ecommerce.modules.product.mapper.ProductMapper;
import com.khahnm04.ecommerce.modules.product.mapper.ProductQuestionMapper;
import com.khahnm04.ecommerce.modules.product.mapper.ProductVariantMapper;
import com.khahnm04.ecommerce.modules.product.service.ProductService;
import com.khahnm04.ecommerce.modules.storage.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private final BrandRepository brandRepository;
    private final CloudinaryService cloudinaryService;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final AttributeRepository attributeRepository;
    private final ProductVariantMapper productVariantMapper;
    private final ProductVariantRepository productVariantRepository;
    private final ProductFaqMapper productFaqMapper;
    private final ProductFaqRepository productFaqRepository;
    private final ProductQuestionMapper productQuestionMapper;
    private final ProductQuestionRepository productQuestionRepository;

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest request, MultipartFile thumbnail) {
        if (productRepository.existsBySlug(request.getSlug())) {
            throw new AppException(ErrorCode.PRODUCT_EXISTED);
        }

        Product product = productMapper.fromProductRequestToProduct(request);
        product.setThumbnail(cloudinaryService.upload(thumbnail));
        setBrand(product, request);
        setCategory(product, request);
        assignAttributeToProduct(product, request);
        uploadImages(product, request.getImages());

        Product savedProduct = productRepository.save(product);
        log.info("Product created with id = {}", savedProduct.getId());
        return productMapper.fromProductToProductResponse(savedProduct);
    }

    @Override
    public PageResponse<ProductResponse> getAllProducts(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, SortUtils.parseSort(sort));
        Page<Product> productPage = productRepository.findAllByDeletedAtIsNull(pageable);
        Page<ProductResponse> dtoPage = productPage.map(productMapper::fromProductToProductResponse);
        return PageResponse.fromPage(dtoPage);
    }

    @Override
    public PageResponse<ProductResponse> getAllDeletedProducts(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, SortUtils.parseSort(sort));
        Page<Product> productPage = productRepository.findAllByDeletedAtIsNotNull(pageable);
        Page<ProductResponse> dtoPage = productPage.map(productMapper::fromProductToProductResponse);
        return PageResponse.fromPage(dtoPage);
    }

    @Override
    public PageResponse<ProductVariantResponse> getProductVariantsByProductId(Long productId, int page, int size, String sort) {
        if (!productRepository.existsById(productId)) {
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        Pageable pageable = PageRequest.of(page, size, SortUtils.parseSort(sort));
        Page<ProductVariant> variantPage = productVariantRepository.findAllByProductIdAndDeletedAtIsNull(productId, pageable);
        return PageResponse.fromPage(variantPage.map(productVariantMapper::toResponse));
    }

    @Override
    public PageResponse<ProductFaqResponse> getFaqsByProductId(Long productId, int page, int size, String sort) {
        if (!productRepository.existsById(productId)) {
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        Pageable pageable = PageRequest.of(page, size, SortUtils.parseSort(sort));
        Page<ProductFaq> faqPage = productFaqRepository.findAllByProductIdAndDeletedAtIsNull(productId, pageable);
        return PageResponse.fromPage(faqPage.map(productFaqMapper::toResponse));
    }

    @Override
    public List<ProductQuestionResponse> getQuestionsByProductId(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        List<ProductQuestion> rootQuestions = productQuestionRepository.findAllQuestionsByProductId(productId);
        return rootQuestions.stream()
                .map(productQuestionMapper::toResponse)
                .toList();
    }

    @Override
    public ProductResponse getProductDetailById(Long id) {
        return productMapper.fromProductToProductResponse(getProductById(id));
    }

    @Override
    public ProductResponse getProductDetailBySlug(String slug) {
        Product product = productRepository.findBySlug(slug)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        return productMapper.fromProductToProductResponse(product);
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest request, MultipartFile thumbnail) {
        Product product = getProductById(id);

        if (StringUtils.hasText(request.getSlug())
                && productRepository.existsBySludIgnoreCaseAndIdNot(request.getSlug(), id)) {
            throw new AppException(ErrorCode.PRODUCT_EXISTED);
        }
        if (request.getBrandId() != null && !request.getBrandId().equals(product.getBrand().getId())) {
            setBrand(product, request);
        }

        productMapper.updateProduct(product, request);

        if (thumbnail != null && !thumbnail.isEmpty()) {
            product.setThumbnail(cloudinaryService.upload(thumbnail));
        }
        if (!CollectionUtils.isEmpty(request.getCategoryIds())) {
            updateCategories(product, request.getCategoryIds());
        }
        if (request.getAttributes() != null) {
            updateAttributes(product, request.getAttributes());
        }
        if (request.getImages() != null) {
            updateImages(product, request.getImages());
        }

        Product savedProduct = productRepository.save(product);
        log.info("Updated product with id = {}", savedProduct.getId());
        return productMapper.fromProductToProductResponse(savedProduct);
    }

    @Override
    public void updateProductStatus(Long id, String status) {
        Product product = getProductById(id);

        boolean isValid = Arrays.stream(ProductStatus.values())
                .anyMatch(e -> e.name().equalsIgnoreCase(status));
        if (!isValid) {
            throw new AppException(ErrorCode.INVALID_ENUM_VALUE);
        }

        product.setStatus(ProductStatus.valueOf(status));
        productRepository.save(product);
        log.info("Updated status product with id = {}", id);
    }

    @Override
    public void softDeleteProduct(Long id) {
        Product product = getProductById(id);
        product.setDeletedAt(LocalDateTime.now());
        productRepository.save(product);
        log.info("Soft deleted product with id {}", id);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
        log.info("Deleted product with id {}", id);
    }

    @Override
    public void restoreProduct(Long id) {
        Product product = getProductById(id);
        product.setDeletedAt(null);
        productRepository.save(product);
        log.info("Restored product with id {}", id);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    private void assignAttributeToProduct(Product product, ProductRequest request) {
        List<Long> attributeIds = request.getAttributes().stream()
                .map(ProductRequest.ProductAttributeRequest::getAttributeId)
                .toList();
        List<Attribute> existingAttributes = attributeRepository.findAllById(attributeIds);
        if (request.getAttributes().size() != existingAttributes.size()) {
            throw new AppException(ErrorCode.ATTRIBUTE_NOT_FOUND);
        }
        Map<Long, String> attributeValueMap = request.getAttributes().stream()
                .collect(Collectors.toMap(
                        ProductRequest.ProductAttributeRequest::getAttributeId,
                        ProductRequest.ProductAttributeRequest::getValue,
                        (v1, v2) -> v1
                ));
        List<ProductAttributeValue> productAttributeValues = existingAttributes.stream()
                .map(attribute -> ProductAttributeValue.builder()
                        .product(product)
                        .attribute(attribute)
                        .value(attributeValueMap.get(attribute.getId()))
                        .build())
                .toList();
        product.setProductAttributeValues(productAttributeValues);
    }

    private void uploadImages(Product product, List<String> images) {
        if (!CollectionUtils.isEmpty(images)) {
            AtomicInteger index = new AtomicInteger(0);
            List<ProductImage> productImages = images
                    .stream()
                    .map(fileUrl -> ProductImage.builder()
                            .imageUrl(fileUrl)
                            .displayOrder(index.getAndIncrement())
                            .product(product)
                            .build())
                    .toList();
            product.setProductImages(productImages);
        }
    }

    /**
     * Đồng bộ danh sách ảnh từ Client gửi lên vào Database.
     * Logic:
     * - Xóa ảnh cũ không còn trong list mới.
     * - Cập nhật thứ tự (displayOrder) cho ảnh cũ vẫn được giữ lại.
     * - Thêm mới ảnh chưa có.
     */
    private void updateImages(Product product, List<String> newImageUrls) {
        if (product.getProductImages() == null) {
            product.setProductImages(new ArrayList<>());
        }

        product.getProductImages()
                .removeIf(img -> !newImageUrls.contains(img.getImageUrl()));

        for (int i = 0; i < newImageUrls.size(); ++i) {
            String url = newImageUrls.get(i);

            Optional<ProductImage> existingImage = product.getProductImages().stream()
                    .filter(img -> img.getImageUrl().equals(url))
                    .findFirst();

            if (existingImage.isPresent()) {
                existingImage.get().setDisplayOrder(i);
            } else {
                ProductImage newImage = ProductImage.builder()
                        .imageUrl(url)
                        .displayOrder(i)
                        .product(product)
                        .build();
                product.getProductImages().add(newImage);
            }
        }
    }

    private void setBrand(Product product, ProductRequest request) {
        Brand existingBrand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_FOUND));
        product.setBrand(existingBrand);
    }

    private void setCategory(Product product, ProductRequest request) {
        List<Category> existingCategories = categoryRepository.findAllById(request.getCategoryIds());
        if (existingCategories.size() != request.getCategoryIds().size()) {
            throw new AppException(ErrorCode.CATEGORY_NOT_FOUND);
        }
        List<ProductCategory> productCategories = existingCategories.stream()
                .map(category -> ProductCategory.builder()
                        .product(product)
                        .category(category)
                        .build())
                .toList();
        product.setProductCategories(productCategories);
    }

    private void updateCategories(Product product, Set<Long> newCategoryIds) {
        product.getProductCategories()
                .removeIf(productCategory -> !newCategoryIds.contains(productCategory.getCategory().getId()));

        Set<Long> existingCategoryIds = product.getProductCategories()
                .stream()
                .map(productCategory -> productCategory.getCategory().getId())
                .collect(Collectors.toSet());

        Set<Long> idsToAdd = newCategoryIds.stream()
                .filter(id -> !existingCategoryIds.contains(id))
                .collect(Collectors.toSet());

        if (!idsToAdd.isEmpty()) {
            List<Category> categories = categoryRepository.findAllById(idsToAdd);
            if (categories.size() != idsToAdd.size()) {
                throw new AppException(ErrorCode.CATEGORY_NOT_FOUND);
            }
            categories.forEach(category -> {
                ProductCategory newProductCategory = ProductCategory.builder()
                        .product(product)
                        .category(category)
                        .build();
                product.addCategory(newProductCategory);
            });
        }
    }

    /**
     * Cập nhật danh sách thuộc tính (Attributes) cho sản phẩm.
     * Logic:
     * 1. Xóa các thuộc tính có trong DB nhưng không có trong Request.
     * 2. Tìm các Attribute Entity từ DB theo list ID gửi lên (tránh query N+1 trong vòng lặp).
     * 3. Nếu thuộc tính đã có -> Cập nhật giá trị (Value).
     * 4. Nếu thuộc tính chưa có -> Tạo mới.
     */
    private void updateAttributes(Product product, List<ProductRequest.ProductAttributeRequest> attributeRequests) {
        Set<Long> requestAttrIds = attributeRequests.stream()
                .map(ProductRequest.ProductAttributeRequest::getAttributeId)
                .collect(Collectors.toSet());

        List<Attribute> attributes = attributeRepository.findAllById(requestAttrIds);
        if (attributes.size() != requestAttrIds.size()) {
            throw new AppException(ErrorCode.ATTRIBUTE_NOT_FOUND);
        }
        Map<Long, Attribute> attributeMap = attributes.stream()
                .collect(Collectors.toMap(Attribute::getId, attr -> attr));

        product.getProductAttributeValues()
                .removeIf(pav -> !requestAttrIds.contains(pav.getAttribute().getId()));

        for (ProductRequest.ProductAttributeRequest req : attributeRequests) {
            Optional<ProductAttributeValue> existingPav = product.getProductAttributeValues().stream()
                    .filter(pav -> pav.getAttribute().getId().equals(req.getAttributeId()))
                    .findFirst();

            if (existingPav.isPresent()) {
                existingPav.get().setValue(req.getValue());
            } else {
                ProductAttributeValue newPav = ProductAttributeValue.builder()
                        .product(product)
                        .attribute(attributeMap.get(req.getAttributeId()))
                        .value(req.getValue())
                        .build();
                product.getProductAttributeValues().add(newPav);
            }
        }
    }

}
