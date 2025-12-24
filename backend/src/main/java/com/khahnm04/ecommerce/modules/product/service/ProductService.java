package com.khahnm04.ecommerce.modules.product.service;

import com.khahnm04.ecommerce.modules.product.dto.request.ProductRequest;
import com.khahnm04.ecommerce.modules.product.dto.response.ProductFaqResponse;
import com.khahnm04.ecommerce.modules.product.dto.response.ProductQuestionResponse;
import com.khahnm04.ecommerce.modules.product.dto.response.ProductResponse;
import com.khahnm04.ecommerce.shared.dto.PageResponse;
import com.khahnm04.ecommerce.modules.product.dto.response.ProductVariantResponse;
import com.khahnm04.ecommerce.modules.product.entity.Product;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductRequest request, MultipartFile thumbnail);
    PageResponse<ProductResponse> getAllProducts(int page, int size, String sort);
    PageResponse<ProductResponse> getAllDeletedProducts(int page, int size, String sort);
    PageResponse<ProductVariantResponse> getProductVariantsByProductId(Long productId, int page, int size, String sort);
    PageResponse<ProductFaqResponse> getFaqsByProductId(Long productId, int page, int size, String sort);
    List<ProductQuestionResponse> getQuestionsByProductId(Long productId);
    Product getProductById(Long id);
    ProductResponse getProductDetailById(Long id);
    ProductResponse getProductDetailBySlug(String slug);
    ProductResponse updateProduct(Long id, ProductRequest request, MultipartFile thumbnail);
    void updateProductStatus(Long id, String status);
    void softDeleteProduct(Long id);
    void deleteProduct(Long id);
    void restoreProduct(Long id);

}
