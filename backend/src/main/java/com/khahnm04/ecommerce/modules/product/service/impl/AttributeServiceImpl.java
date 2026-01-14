package com.khahnm04.ecommerce.modules.product.service.impl;

import com.khahnm04.ecommerce.shared.util.SortUtils;
import com.khahnm04.ecommerce.modules.product.dto.request.AttributeRequest;
import com.khahnm04.ecommerce.shared.dto.PageResponse;
import com.khahnm04.ecommerce.modules.product.dto.response.AttributeResponse;
import com.khahnm04.ecommerce.modules.product.entity.Attribute;
import com.khahnm04.ecommerce.core.exception.AppException;
import com.khahnm04.ecommerce.core.exception.ErrorCode;
import com.khahnm04.ecommerce.modules.product.mapper.AttributeMapper;
import com.khahnm04.ecommerce.modules.product.repository.AttributeRepository;
import com.khahnm04.ecommerce.modules.product.service.AttributeService;
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
public class AttributeServiceImpl implements AttributeService {

    private final AttributeRepository attributeRepository;
    private final AttributeMapper attributeMapper;

    @Override
    public AttributeResponse createAttribute(AttributeRequest request) {
        if (attributeRepository.existsByCode(request.getCode())) {
            throw new AppException(ErrorCode.ATTRIBUTE_EXISTED);
        }
        Attribute attribute = attributeMapper.fromAttributeRequestToAttribute(request);
        Attribute savedAttribute = attributeRepository.save(attribute);
        log.info("Attribute created with id = {}", savedAttribute.getId());
        return attributeMapper.fromAttributeToAttributeResponse(savedAttribute);
    }

    @Override
    public PageResponse<AttributeResponse> getAllAttributes(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, SortUtils.parseSort(sort));
        Page<Attribute> attributePage = attributeRepository.findAllByDeletedAtIsNull(pageable);
        Page<AttributeResponse> dtoPage = attributePage.map(attributeMapper::fromAttributeToAttributeResponse);
        return PageResponse.fromPage(dtoPage);
    }

    @Override
    public PageResponse<AttributeResponse> getAllDeletedAttributes(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, SortUtils.parseSort(sort));
        Page<Attribute> attributePage = attributeRepository.findAllByDeletedAtIsNotNull(pageable);
        Page<AttributeResponse> dtoPage = attributePage.map(attributeMapper::fromAttributeToAttributeResponse);
        return PageResponse.fromPage(dtoPage);
    }

    @Override
    public AttributeResponse getAttributeDetailById(Long id) {
        return attributeMapper.fromAttributeToAttributeResponse(getAttributeById(id));
    }

    @Override
    public AttributeResponse getAttributeDetailByCode(String code) {
        Attribute attribute = attributeRepository.findByCode(code)
                .orElseThrow(() -> new AppException(ErrorCode.ATTRIBUTE_NOT_FOUND));
        return attributeMapper.fromAttributeToAttributeResponse(attribute);
    }

    @Override
    public AttributeResponse updateAttribute(Long id, AttributeRequest request) {
        Attribute attribute = getAttributeById(id);
        if (StringUtils.hasText(request.getCode())
                && attributeRepository.existsByCodeIgnoreCaseAndIdNot(request.getCode(), id)) {
            throw new AppException(ErrorCode.ATTRIBUTE_EXISTED);
        }
        attributeMapper.updateAttribute(attribute, request);
        Attribute savedAttribute = attributeRepository.save(attribute);
        log.info("Attribute updated with id = {}", savedAttribute.getId());
        return attributeMapper.fromAttributeToAttributeResponse(savedAttribute);
    }

    @Override
    public void softDeleteAttribute(Long id) {
        Attribute attribute = getAttributeById(id);
        if (attribute.getDeletedAt() != null) {
            throw new AppException(ErrorCode.ATTRIBUTE_ALREADY_SOFT_DELETED);
        }
        attribute.setDeletedAt(LocalDateTime.now());
        attributeRepository.save(attribute);
        log.info("Attribute soft deleted with id = {}", attribute.getId());
    }

    @Override
    public void deleteAttribute(Long id) {
        attributeRepository.delete(getAttributeById(id));
        log.info("Attribute deleted with id = {}", id);
    }

    @Override
    public void restoreAttribute(Long id) {
        Attribute attribute = getAttributeById(id);
        attribute.setDeletedAt(null);
        attributeRepository.save(attribute);
        log.info("Attribute restored with id = {}", attribute.getId());
    }

    private Attribute getAttributeById(Long id) {
        return attributeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ATTRIBUTE_NOT_FOUND));
    }

}
