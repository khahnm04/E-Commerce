package com.khahnm04.ecommerce.service.brand;

import com.khahnm04.ecommerce.dto.request.brand.BrandRequest;
import com.khahnm04.ecommerce.dto.response.brand.BrandResponse;
import com.khahnm04.ecommerce.dto.response.PageResponse;
import com.khahnm04.ecommerce.dto.response.product.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

public interface BrandService {

    BrandResponse createBrand(BrandRequest request);
    PageResponse<BrandResponse> getAllBrands(int page, int size, String sort);
    PageResponse<BrandResponse> getAllDeletedBrands(int page, int size, String sort);
    PageResponse<ProductResponse> getAllProductsByBrandId(int page, int size, String sort, Long id);
    BrandResponse getBrandDetailById(Long id);
    BrandResponse getBrandDetailBySlug(String slug);
    BrandResponse updateBrand(Long id, BrandRequest request, MultipartFile file);
    void updateBrandStatus(Long id, String status);
    void softDeleteBrand(Long id);
    void deleteBrand(Long id);
    void restoreBrand(Long id);

}
