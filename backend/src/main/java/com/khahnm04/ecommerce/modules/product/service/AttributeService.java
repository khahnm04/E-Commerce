package com.khahnm04.ecommerce.modules.product.service;

import com.khahnm04.ecommerce.modules.product.dto.request.AttributeRequest;
import com.khahnm04.ecommerce.shared.dto.PageResponse;
import com.khahnm04.ecommerce.modules.product.dto.response.AttributeResponse;

public interface AttributeService {

    AttributeResponse createAttribute(AttributeRequest request);
    PageResponse<AttributeResponse> getAllAttributes(int page, int size, String sort);
    PageResponse<AttributeResponse> getAllDeletedAttributes(int page, int size, String sort);
    AttributeResponse getAttributeDetailById(Long id);
    AttributeResponse getAttributeDetailByCode(String code);
    AttributeResponse updateAttribute(Long id, AttributeRequest request);
    void softDeleteAttribute(Long id);
    void deleteAttribute(Long id);
    void restoreAttribute(Long id);

}
