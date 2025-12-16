package com.khahnm04.ecommerce.service.product;

import com.khahnm04.ecommerce.dto.request.product.VariantValueRequest;
import com.khahnm04.ecommerce.dto.response.PageResponse;
import com.khahnm04.ecommerce.dto.response.product.VariantValueResponse;

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
