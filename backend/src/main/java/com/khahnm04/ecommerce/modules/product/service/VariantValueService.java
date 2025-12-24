package com.khahnm04.ecommerce.modules.product.service;

import com.khahnm04.ecommerce.modules.product.dto.request.VariantValueRequest;
import com.khahnm04.ecommerce.shared.dto.PageResponse;
import com.khahnm04.ecommerce.modules.product.dto.response.VariantValueResponse;

public interface VariantValueService {

    VariantValueResponse createVariantValue(VariantValueRequest request);
    PageResponse<VariantValueResponse> getAllVariantValues(int page, int size, String sort);
    PageResponse<VariantValueResponse> getAllDeletedVariantValues(int page, int size, String sort);
    VariantValueResponse getDetailById(Long id);
    VariantValueResponse updateVariantValue(Long id, VariantValueRequest request);
    void softDeleteVariantValue(Long id);
    void deleteVariantValue(Long id);
    void restoreVariantValue(Long id);

}
