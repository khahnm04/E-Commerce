package com.khahnm04.ecommerce.service.product;

import com.khahnm04.ecommerce.dto.request.product.ProductRequest;
import com.khahnm04.ecommerce.dto.response.product.ProductFaqResponse;
import com.khahnm04.ecommerce.dto.response.product.ProductQuestionResponse;
import com.khahnm04.ecommerce.dto.response.product.ProductResponse;
import com.khahnm04.ecommerce.dto.response.PageResponse;
import com.khahnm04.ecommerce.dto.response.product.ProductVariantResponse;
import com.khahnm04.ecommerce.entity.product.Product;
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
