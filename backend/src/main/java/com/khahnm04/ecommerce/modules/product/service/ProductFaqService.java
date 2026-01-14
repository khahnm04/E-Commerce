package com.khahnm04.ecommerce.modules.product.service;

import com.khahnm04.ecommerce.modules.product.dto.request.ProductFaqRequest;
import com.khahnm04.ecommerce.modules.product.dto.response.ProductFaqResponse;

public interface ProductFaqService {

    ProductFaqResponse createProductFaq(ProductFaqRequest request);

    ProductFaqResponse getDetailById(Long id);

    ProductFaqResponse updateProductFaq(Long id, ProductFaqRequest request);

    void softDeleteProductFaq(Long id);

    void deleteProductFaq(Long id);

    void restoreProductFaq(Long id);

}
