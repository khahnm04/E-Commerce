package com.khahnm04.ecommerce.modules.brand.service;

import com.khahnm04.ecommerce.shared.enums.BrandStatus;
import com.khahnm04.ecommerce.shared.util.SortUtils;
import com.khahnm04.ecommerce.modules.brand.dto.request.BrandRequest;
import com.khahnm04.ecommerce.shared.dto.PageResponse;
import com.khahnm04.ecommerce.modules.brand.dto.response.BrandResponse;
import com.khahnm04.ecommerce.modules.product.dto.response.ProductResponse;
import com.khahnm04.ecommerce.modules.brand.entity.Brand;
import com.khahnm04.ecommerce.modules.product.entity.Product;
import com.khahnm04.ecommerce.core.exception.AppException;
import com.khahnm04.ecommerce.core.exception.ErrorCode;
import com.khahnm04.ecommerce.modules.brand.mapper.BrandMapper;
import com.khahnm04.ecommerce.modules.product.mapper.ProductMapper;
import com.khahnm04.ecommerce.modules.brand.repository.BrandRepository;
import com.khahnm04.ecommerce.modules.product.repository.ProductRepository;
import com.khahnm04.ecommerce.modules.storage.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;
    private final ProductMapper productMapper;
    private final CloudinaryService cloudinaryService;
    private final ProductRepository productRepository;

    @Override
    public BrandResponse createBrand(BrandRequest request) {
        if (brandRepository.existsBySlug(request.getSlug())) {
            throw new AppException(ErrorCode.BRAND_EXISTED);
        }
        if (brandRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.BRAND_EXISTED);
        }

        Brand brand = brandMapper.fromBrandRequestToBrand(request);
        brand.setLogo(cloudinaryService.upload(request.getLogo()));

        Brand savedBrand = brandRepository.save(brand);
        log.info("Brand created with id = {}", savedBrand.getId());
        return brandMapper.toBrandResponse(savedBrand);
    }

    @Override
    public PageResponse<BrandResponse> getAllBrands(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, SortUtils.parseSort(sort));
        Page<Brand> brandPage = brandRepository.findAllByDeletedAtIsNull(pageable);
        Page<BrandResponse> dtoPage = brandPage.map(brandMapper::toBrandResponse);
        return PageResponse.fromPage(dtoPage);
    }

    @Override
    public PageResponse<BrandResponse> getAllDeletedBrands(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, SortUtils.parseSort(sort));
        Page<Brand> brandPage = brandRepository.findAllByDeletedAtIsNotNull(pageable);
        Page<BrandResponse> dtoPage = brandPage.map(brandMapper::toBrandResponse);
        return PageResponse.fromPage(dtoPage);
    }

    @Override
    public PageResponse<ProductResponse> getAllProductsByBrandId(int page, int size, String sort, Long id) {
        getBrandById(id);
        Pageable pageable = PageRequest.of(page, size, SortUtils.parseSort(sort));
        Page<Product> productPage = productRepository.findAllByBrandId(id, pageable);
        Page<ProductResponse> dtoPage = productPage.map(productMapper::fromProductToProductResponse);
        return PageResponse.fromPage(dtoPage);
    }

    @Override
    public BrandResponse getBrandDetailById(Long id) {
        return brandMapper.toBrandResponse(getBrandById(id));
    }

    @Override
    public BrandResponse getBrandDetailBySlug(String slug) {
        Brand brand = brandRepository.findBySlug(slug)
                .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_FOUND));
        return brandMapper.toBrandResponse(brand);
    }

    @Override
    public BrandResponse updateBrand(Long id, BrandRequest request, MultipartFile file) {
        Brand brand = getBrandById(id);

        if (!Objects.equals(brand.getName(), request.getName()) && brandRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.BRAND_EXISTED);
        }
        if (!Objects.equals(brand.getSlug(), request.getSlug()) && brandRepository.existsBySlug(request.getSlug())) {
            throw new AppException(ErrorCode.BRAND_EXISTED);
        }

        brandMapper.updateBrand(brand, request);
        brand.setLogo(cloudinaryService.upload(file));

        Brand savedBrand = brandRepository.save(brand);
        log.info("Updated brand with id = {}", savedBrand.getId());
        return brandMapper.toBrandResponse(savedBrand);
    }

    @Override
    public void updateBrandStatus(Long id, String status) {
        Brand brand = getBrandById(id);

        boolean isValid = Arrays.stream(BrandStatus.values())
                .anyMatch(e -> e.name().equalsIgnoreCase(status));
        if (!isValid) {
            throw new AppException(ErrorCode.INVALID_ENUM_VALUE);
        }

        brand.setStatus(BrandStatus.valueOf(status));
        brandRepository.save(brand);
        log.info("Updated status brand with id = {}", brand.getId());
    }

    @Override
    public void softDeleteBrand(Long id) {
        Brand brand = getBrandById(id);
        brand.setDeletedAt(LocalDateTime.now());
        brandRepository.save(brand);
        log.info("Soft deleted brand with id {}", id);
    }

    @Override
    public void deleteBrand(Long id) {
        Brand brand = getBrandById(id);
        brandRepository.delete(brand);
        log.info("Deleted brand with id {}", id);
    }

    @Override
    public void restoreBrand(Long id) {
        Brand brand = getBrandById(id);
        brand.setDeletedAt(null);
        brandRepository.save(brand);
        log.info("Brand restored with id {}", id);
    }

    private Brand getBrandById(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_FOUND));
    }

}
