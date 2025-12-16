package com.khahnm04.ecommerce.service.product;

import com.khahnm04.ecommerce.dto.request.product.AttributeRequest;
import com.khahnm04.ecommerce.dto.response.PageResponse;
import com.khahnm04.ecommerce.dto.response.product.AttributeResponse;

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
