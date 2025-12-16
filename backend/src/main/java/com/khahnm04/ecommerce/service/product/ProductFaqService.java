package com.khahnm04.ecommerce.service.product;

import com.khahnm04.ecommerce.dto.request.product.ProductFaqRequest;
import com.khahnm04.ecommerce.dto.response.product.ProductFaqResponse;

public interface ProductFaqService {

    ProductFaqResponse createProductFaq(ProductFaqRequest request);

    ProductFaqResponse getDetailById(Long id);

    ProductFaqResponse updateProductFaq(Long id, ProductFaqRequest request);

    void softDeleteProductFaq(Long id);

    void deleteProductFaq(Long id);

    void restoreProductFaq(Long id);

}
