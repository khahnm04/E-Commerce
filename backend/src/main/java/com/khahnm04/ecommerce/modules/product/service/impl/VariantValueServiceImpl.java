package com.khahnm04.ecommerce.modules.product.service.impl;

import com.khahnm04.ecommerce.shared.util.SortUtils;
import com.khahnm04.ecommerce.modules.product.dto.request.VariantValueRequest;
import com.khahnm04.ecommerce.shared.dto.PageResponse;
import com.khahnm04.ecommerce.modules.product.dto.response.VariantValueResponse;
import com.khahnm04.ecommerce.modules.product.entity.Variant;
import com.khahnm04.ecommerce.modules.product.entity.VariantValue;
import com.khahnm04.ecommerce.core.exception.AppException;
import com.khahnm04.ecommerce.core.exception.ErrorCode;
import com.khahnm04.ecommerce.modules.product.mapper.VariantValueMapper;
import com.khahnm04.ecommerce.modules.product.repository.VariantRepository;
import com.khahnm04.ecommerce.modules.product.repository.VariantValueRepository;
import com.khahnm04.ecommerce.modules.product.service.VariantValueService;
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
public class VariantValueServiceImpl implements VariantValueService {

    private final VariantValueRepository variantValueRepository;
    private final VariantRepository variantRepository;
    private final VariantValueMapper variantValueMapper;

    @Override
    public VariantValueResponse createVariantValue(VariantValueRequest request) {
        if (variantValueRepository.existsByVariantIdAndValue(request.getVariantId(), request.getValue())) {
            throw new AppException(ErrorCode.VARIANT_VALUE_EXISTED);
        }

        Variant variant = variantRepository.findById(request.getVariantId())
                .orElseThrow(() -> new AppException(ErrorCode.VARIANT_NOT_FOUND));

        VariantValue variantValue = variantValueMapper.fromRequestToEntity(request);
        variantValue.setVariant(variant);

        if (request.getDisplayOrder() == null) {
            variantValue.setDisplayOrder(0);
        }

        VariantValue savedValue = variantValueRepository.save(variantValue);
        log.info("Variant Value created with id = {}", savedValue.getId());
        return variantValueMapper.toResponse(savedValue);
    }

    @Override
    public PageResponse<VariantValueResponse> getAllVariantValues(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, SortUtils.parseSort(sort));
        Page<VariantValue> valuePage = variantValueRepository.findAllByDeletedAtIsNull(pageable);
        return PageResponse.fromPage(valuePage.map(variantValueMapper::toResponse));
    }

    @Override
    public PageResponse<VariantValueResponse> getAllDeletedVariantValues(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, SortUtils.parseSort(sort));
        Page<VariantValue> valuePage = variantValueRepository.findAllByDeletedAtIsNotNull(pageable);
        return PageResponse.fromPage(valuePage.map(variantValueMapper::toResponse));
    }

    @Override
    public VariantValueResponse getDetailById(Long id) {
        return variantValueMapper.toResponse(getById(id));
    }

    @Override
    public VariantValueResponse updateVariantValue(Long id, VariantValueRequest request) {
        VariantValue variantValue = getById(id);

        Long targetVariantId = (request.getVariantId() != null) ? request.getVariantId() : variantValue.getVariant().getId();
        if (request.getVariantId() != null && !request.getVariantId().equals(variantValue.getVariant().getId())) {
            Variant newVariant = variantRepository.findById(targetVariantId)
                    .orElseThrow(() -> new AppException(ErrorCode.VARIANT_NOT_FOUND));
            variantValue.setVariant(newVariant);
        }

        if (StringUtils.hasText(request.getValue())
                && variantValueRepository.existsByVariantIdAndValueIgnoreCaseAndIdNot(targetVariantId, request.getValue(), id)) {
            throw new AppException(ErrorCode.VARIANT_VALUE_EXISTED);
        }

        variantValueMapper.updateEntity(variantValue, request);
        VariantValue savedValue = variantValueRepository.save(variantValue);

        log.info("Variant Value updated with id = {}", savedValue.getId());
        return variantValueMapper.toResponse(savedValue);
    }

    @Override
    public void softDeleteVariantValue(Long id) {
        VariantValue variantValue = getById(id);
        if (variantValue.getDeletedAt() != null) {
            throw new AppException(ErrorCode.VARIANT_VALUE_ALREADY_SOFT_DELETED);
        }
        variantValue.setDeletedAt(LocalDateTime.now());
        variantValueRepository.save(variantValue);
        log.info("Variant Value soft deleted with id = {}", id);
    }

    @Override
    public void deleteVariantValue(Long id) {
        VariantValue variantValue = getById(id);
        variantValueRepository.delete(variantValue);
        log.info("Variant Value hard deleted with id = {}", id);
    }

    @Override
    public void restoreVariantValue(Long id) {
        VariantValue variantValue = getById(id);
        variantValue.setDeletedAt(null);
        variantValueRepository.save(variantValue);
        log.info("Variant Value restored with id = {}", id);
    }

    private VariantValue getById(Long id) {
        return variantValueRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.VARIANT_VALUE_NOT_FOUND));
    }

}
