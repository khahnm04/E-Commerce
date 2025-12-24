package com.khahnm04.ecommerce.modules.product.service;

import com.khahnm04.ecommerce.modules.product.dto.request.VariantRequest;
import com.khahnm04.ecommerce.shared.dto.PageResponse;
import com.khahnm04.ecommerce.modules.product.dto.response.VariantResponse;
import com.khahnm04.ecommerce.modules.product.dto.response.VariantValueResponse;

public interface VariantService {

    VariantResponse createVariant(VariantRequest request);
    PageResponse<VariantResponse> getAllVariants(int page, int size, String sort);
    PageResponse<VariantResponse> getAllDeletedVariants(int page, int size, String sort);
    PageResponse<VariantValueResponse> getVariantValuesByVariantId(Long variantId, int page, int size, String sort);
    VariantResponse getVariantDetailById(Long id);
    VariantResponse getVariantDetailByCode(String code);
    VariantResponse updateVariant(Long id, VariantRequest request);
    void softDeleteVariant(Long id);
    void deleteVariant(Long id);
    void restoreVariant(Long id);

}
