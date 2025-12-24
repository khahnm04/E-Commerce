package com.khahnm04.ecommerce.modules.product.service.impl;

import com.khahnm04.ecommerce.shared.util.SortUtils;
import com.khahnm04.ecommerce.modules.product.dto.request.ProductVariantRequest;
import com.khahnm04.ecommerce.shared.dto.PageResponse;
import com.khahnm04.ecommerce.modules.product.dto.response.ProductVariantResponse;
import com.khahnm04.ecommerce.modules.product.entity.Product;
import com.khahnm04.ecommerce.modules.product.entity.ProductVariant;
import com.khahnm04.ecommerce.modules.product.entity.ProductVariantValue;
import com.khahnm04.ecommerce.modules.product.entity.VariantValue;
import com.khahnm04.ecommerce.core.exception.AppException;
import com.khahnm04.ecommerce.core.exception.ErrorCode;
import com.khahnm04.ecommerce.modules.product.mapper.ProductVariantMapper;
import com.khahnm04.ecommerce.modules.product.repository.ProductRepository;
import com.khahnm04.ecommerce.modules.product.repository.ProductVariantRepository;
import com.khahnm04.ecommerce.modules.product.repository.VariantValueRepository;
import com.khahnm04.ecommerce.modules.product.service.ProductVariantService;
import com.khahnm04.ecommerce.modules.storage.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductVariantServiceImpl implements ProductVariantService {

    private final ProductVariantRepository productVariantRepository;
    private final VariantValueRepository variantValueRepository;
    private final ProductVariantMapper productVariantMapper;
    private final ProductRepository productRepository;
    private final CloudinaryService cloudinaryService;

    @Override
    @Transactional
    public ProductVariantResponse createProductVariant(ProductVariantRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        String finalSku = request.getSku();
        if (!StringUtils.hasText(finalSku)) {
            finalSku = generateSku(product.getName());
        }

        if (productVariantRepository.existsBySku(finalSku)) {
            throw new AppException(ErrorCode.PRODUCT_VARIANT_EXISTED);
        }

        ProductVariant productVariant = productVariantMapper.fromRequestToEntity(request);
        productVariant.setProduct(product);
        productVariant.setImage(cloudinaryService.upload(request.getImage()));
        productVariant.setSku(finalSku);

        assignVariantValuesToProductVariant(productVariant, request.getVariantValueIds());

        ProductVariant savedProductVariant = productVariantRepository.save(productVariant);
        log.info("Product Variant created with id = {}", savedProductVariant.getId());
        return productVariantMapper.toResponse(savedProductVariant);
    }

    @Override
    public PageResponse<ProductVariantResponse> getAllProductVariants(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, SortUtils.parseSort(sort));
        Page<ProductVariant> variantPage = productVariantRepository.findAllByDeletedAtIsNull(pageable);
        return PageResponse.fromPage(variantPage.map(productVariantMapper::toResponse));
    }

    @Override
    public PageResponse<ProductVariantResponse> getAllDeletedProductVariants(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, SortUtils.parseSort(sort));
        Page<ProductVariant> variantPage = productVariantRepository.findAllByDeletedAtIsNotNull(pageable);
        return PageResponse.fromPage(variantPage.map(productVariantMapper::toResponse));
    }

    @Override
    public ProductVariantResponse getDetailById(Long id) {
        return productVariantMapper.toResponse(getById(id));
    }

    @Override
    public ProductVariantResponse getDetailBySku(String sku) {
        ProductVariant variant = productVariantRepository.findBySku(sku)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIANT_NOT_FOUND));
        return productVariantMapper.toResponse(variant);
    }

    @Override
    @Transactional
    public ProductVariantResponse updateProductVariant(Long id, ProductVariantRequest request, MultipartFile file) {
        ProductVariant productVariant = getById(id);

        if (StringUtils.hasText(request.getSku())
                && productVariantRepository.existsBySkuIgnoreCaseAndIdNot(request.getSku(), id)) {
            throw new AppException(ErrorCode.PRODUCT_VARIANT_EXISTED);
        }

        if (request.getProductId() != null && !request.getProductId().equals(productVariant.getProduct().getId())) {
            Product newProduct = productRepository.findById(request.getProductId())
                    .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
            productVariant.setProduct(newProduct);
        }

        productVariantMapper.updateEntity(productVariant, request);
        if (file != null && !file.isEmpty()) {
            productVariant.setImage(cloudinaryService.upload(file));
        }
        updateVariantValues(productVariant, request.getVariantValueIds());

        ProductVariant savedVariant = productVariantRepository.save(productVariant);
        log.info("Product Variant updated with id = {}", savedVariant.getId());
        return productVariantMapper.toResponse(savedVariant);
    }

    @Override
    public void softDeleteProductVariant(Long id) {
        ProductVariant variant = getById(id);
        if (variant.getDeletedAt() != null) {
            throw new AppException(ErrorCode.PRODUCT_VARIANT_ALREADY_SOFT_DELETED);
        }
        variant.setDeletedAt(LocalDateTime.now());
        productVariantRepository.save(variant);
        log.info("Product Variant soft deleted with id = {}", id);
    }

    @Override
    public void deleteProductVariant(Long id) {
        ProductVariant variant = getById(id);
        productVariantRepository.delete(variant);
        log.info("Product Variant hard deleted with id = {}", id);
    }

    @Override
    public void restoreProductVariant(Long id) {
        ProductVariant variant = getById(id);
        variant.setDeletedAt(null);
        productVariantRepository.save(variant);
        log.info("Product Variant restored with id = {}", id);
    }

    private ProductVariant getById(Long id) {
        return productVariantRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIANT_NOT_FOUND));
    }

    private void assignVariantValuesToProductVariant(ProductVariant productVariant, Set<Long> variantValueIds) {
        List<VariantValue> variantValues = variantValueRepository.findAllById(variantValueIds);
        if (variantValues.size() != variantValueIds.size()) {
            throw new AppException(ErrorCode.VARIANT_VALUE_NOT_FOUND);
        }
        variantValues.forEach(variantValue -> {
            ProductVariantValue pvv = ProductVariantValue.builder()
                    .productVariant(productVariant)
                    .variantValue(variantValue)
                    .build();
            productVariant.addVariantValue(pvv);
        });
    }

    /**
     * Cập nhật danh sách các giá trị biến thể (Variant Values) cho một biến thể sản phẩm (Product Variant).
     * <p>
     * Hàm này thực hiện cơ chế đồng bộ hóa (Sync):
     * <ul>
     * <li><b>Xóa bỏ:</b> Các giá trị hiện có trong DB nhưng KHÔNG nằm trong danh sách mới gửi lên.</li>
     * <li><b>Giữ nguyên:</b> Các giá trị đã tồn tại trong cả DB và danh sách mới.</li>
     * <li><b>Thêm mới:</b> Các giá trị chưa có trong DB nhưng có trong danh sách mới.</li>
     * </ul>
     *
     * @param productVariant    Đối tượng biến thể sản phẩm cần cập nhật (Entity).
     * Danh sách {@code productVariantValues} bên trong sẽ bị thay đổi trực tiếp (Mutable).
     * @param newVariantValueIds Tập hợp (Set) các ID của {@code VariantValue} mới mà người dùng muốn gán.
     * Sử dụng {@code Set} để đảm bảo tính duy nhất.
     * @throws AppException Nếu tìm thấy ID nào trong {@code newVariantValueIds} cần thêm mới nhưng không tồn tại trong DB
     * (Mã lỗi: {@code VARIANT_VALUE_NOT_FOUND}).
     */
    private void updateVariantValues(ProductVariant productVariant, Set<Long> newVariantValueIds) {
        productVariant.getProductVariantValues()
                .removeIf(pvv -> !newVariantValueIds.contains(pvv.getVariantValue().getId()));

        Set<Long> existingVariantValueIds = productVariant.getProductVariantValues()
                .stream()
                .map(pvv -> pvv.getVariantValue().getId())
                .collect(Collectors.toSet());

        List<Long> idsToAdd = newVariantValueIds.stream()
                .filter(id -> !existingVariantValueIds.contains(id))
                .toList();

        if (!idsToAdd.isEmpty()) {
            List<VariantValue> newValues = variantValueRepository.findAllById(idsToAdd);
            if (newValues.size() != idsToAdd.size()) {
                throw new AppException(ErrorCode.VARIANT_VALUE_NOT_FOUND);
            }
            newValues.forEach(variantValue -> {
                ProductVariantValue newPvv = ProductVariantValue.builder()
                        .productVariant(productVariant)
                        .variantValue(variantValue)
                        .build();
                productVariant.addVariantValue(newPvv);
            });
        }
    }

    private String generateSku(String productName) {
        String prefix = productName.replaceAll("\\s+", "").toUpperCase();
        if (prefix.length() > 10) prefix = prefix.substring(0, 10);
        return prefix + "-" + System.currentTimeMillis();
    }

}
