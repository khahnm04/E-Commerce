package com.khahnm04.ecommerce.modules.brand.service;

import com.khahnm04.ecommerce.modules.brand.dto.request.BrandRequest;
import com.khahnm04.ecommerce.modules.brand.dto.response.BrandResponse;
import com.khahnm04.ecommerce.shared.dto.PageResponse;
import com.khahnm04.ecommerce.modules.product.dto.response.ProductResponse;
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
