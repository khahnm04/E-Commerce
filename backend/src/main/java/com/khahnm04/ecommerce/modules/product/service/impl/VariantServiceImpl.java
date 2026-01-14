package com.khahnm04.ecommerce.modules.product.service.impl;

import com.khahnm04.ecommerce.shared.util.SortUtils;
import com.khahnm04.ecommerce.modules.product.dto.request.VariantRequest;
import com.khahnm04.ecommerce.shared.dto.PageResponse;
import com.khahnm04.ecommerce.modules.product.dto.response.VariantResponse;
import com.khahnm04.ecommerce.modules.product.dto.response.VariantValueResponse;
import com.khahnm04.ecommerce.modules.product.entity.Variant;
import com.khahnm04.ecommerce.modules.product.entity.VariantValue;
import com.khahnm04.ecommerce.core.exception.AppException;
import com.khahnm04.ecommerce.core.exception.ErrorCode;
import com.khahnm04.ecommerce.modules.product.mapper.VariantMapper;
import com.khahnm04.ecommerce.modules.product.mapper.VariantValueMapper;
import com.khahnm04.ecommerce.modules.product.repository.VariantRepository;
import com.khahnm04.ecommerce.modules.product.repository.VariantValueRepository;
import com.khahnm04.ecommerce.modules.product.service.VariantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class VariantServiceImpl implements VariantService {

    private final VariantRepository variantRepository;
    private final VariantMapper variantMapper;
    private final VariantValueRepository variantValueRepository;
    private final VariantValueMapper variantValueMapper;

    @Override
    public VariantResponse createVariant(VariantRequest request) {
        if (variantRepository.existsByCode(request.getCode())) {
            throw new AppException(ErrorCode.VARIANT_EXISTED);
        }
        Variant variant = variantMapper.fromVariantRequestToVariant(request);
        Variant savedVariant = variantRepository.save(variant);
        log.info("Variant created with id = {}", savedVariant.getId());
        return variantMapper.fromVariantToVariantResponse(savedVariant);
    }

    @Override
    public PageResponse<VariantResponse> getAllVariants(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, SortUtils.parseSort(sort));
        Page<Variant> variantPage = variantRepository.findAllByDeletedAtIsNull(pageable);
        Page<VariantResponse> dtoPage = variantPage.map(variantMapper::fromVariantToVariantResponse);
        return PageResponse.fromPage(dtoPage);
    }

    @Override
    public PageResponse<VariantResponse> getAllDeletedVariants(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, SortUtils.parseSort(sort));
        Page<Variant> variantPage = variantRepository.findAllByDeletedAtIsNotNull(pageable);
        Page<VariantResponse> dtoPage = variantPage.map(variantMapper::fromVariantToVariantResponse);
        return PageResponse.fromPage(dtoPage);
    }

    @Override
    public PageResponse<VariantValueResponse> getVariantValuesByVariantId(Long variantId, int page, int size, String sort) {
        if (!variantRepository.existsById(variantId)) {
            throw new AppException(ErrorCode.VARIANT_NOT_FOUND);
        }
        Pageable pageable = PageRequest.of(page, size, SortUtils.parseSort(sort));
        Page<VariantValue> valuePage = variantValueRepository.findAllByVariantIdAndDeletedAtIsNull(variantId, pageable);
        return PageResponse.fromPage(valuePage.map(variantValueMapper::toResponse));
    }

    @Override
    public VariantResponse getVariantDetailById(Long id) {
        return variantMapper.fromVariantToVariantResponse(getVariantById(id));
    }

    @Override
    public VariantResponse getVariantDetailByCode(String code) {
        Variant variant = variantRepository.findByCode(code)
                .orElseThrow(() -> new AppException(ErrorCode.VARIANT_NOT_FOUND));
        return variantMapper.fromVariantToVariantResponse(variant);
    }

    @Override
    public VariantResponse updateVariant(Long id, VariantRequest request) {
        Variant variant = getVariantById(id);
        if (StringUtils.hasText(request.getCode())
                && variantRepository.existsByCodeIgnoreCaseAndIdNot(request.getCode(), id)) {
            throw new AppException(ErrorCode.VARIANT_EXISTED);
        }
        variantMapper.updateVariant(variant, request);
        Variant savedVariant = variantRepository.save(variant);
        log.info("Variant updated with id = {}", savedVariant.getId());
        return variantMapper.fromVariantToVariantResponse(savedVariant);
    }

    @Override
    public void softDeleteVariant(Long id) {
        Variant variant = getVariantById(id);
        if (variant.getDeletedAt() != null) {
            throw new AppException(ErrorCode.VARIANT_ALREADY_SOFT_DELETED);
        }
        variant.setDeletedAt(LocalDateTime.now());
        variantRepository.save(variant);
        log.info("Variant soft deleted with id = {}", variant.getId());
    }

    @Override
    public void deleteVariant(Long id) {
        variantRepository.delete(getVariantById(id));
        log.info("Variant deleted with id = {}", id);
    }

    @Override
    public void restoreVariant(Long id) {
        Variant variant = getVariantById(id);
        variant.setDeletedAt(null);
        variantRepository.save(variant);
        log.info("Variant restored with id = {}", variant.getId());
    }

    private Variant getVariantById(Long id) {
        return variantRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.VARIANT_NOT_FOUND));
    }

}
