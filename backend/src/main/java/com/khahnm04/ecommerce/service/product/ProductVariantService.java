package com.khahnm04.ecommerce.service.product;

import com.khahnm04.ecommerce.dto.request.product.ProductVariantRequest;
import com.khahnm04.ecommerce.dto.response.PageResponse;
import com.khahnm04.ecommerce.dto.response.product.ProductVariantResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ProductVariantService {

    ProductVariantResponse createProductVariant(ProductVariantRequest request);
    PageResponse<ProductVariantResponse> getAllProductVariants(int page, int size, String sort);
    PageResponse<ProductVariantResponse> getAllDeletedProductVariants(int page, int size, String sort);
    ProductVariantResponse getDetailById(Long id);
    ProductVariantResponse getDetailBySku(String sku);
    ProductVariantResponse updateProductVariant(Long id, ProductVariantRequest request, MultipartFile file);
    void softDeleteProductVariant(Long id);
    void deleteProductVariant(Long id);
    void restoreProductVariant(Long id);

}
