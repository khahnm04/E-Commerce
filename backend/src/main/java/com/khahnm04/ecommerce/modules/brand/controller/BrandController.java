package com.khahnm04.ecommerce.modules.brand.controller;

import com.khahnm04.ecommerce.modules.brand.dto.request.BrandRequest;
import com.khahnm04.ecommerce.shared.dto.ApiResponse;
import com.khahnm04.ecommerce.modules.brand.dto.response.BrandResponse;
import com.khahnm04.ecommerce.shared.dto.PageResponse;
import com.khahnm04.ecommerce.modules.product.dto.response.ProductResponse;
import com.khahnm04.ecommerce.modules.brand.service.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("${api.prefix}/admin/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<BrandResponse> createBrand(
            @Valid @ModelAttribute BrandRequest request
    ) {
        return ApiResponse.<BrandResponse>builder()
                .data(brandService.createBrand(request))
                .message("brand created successfully")
                .build();
    }

    @GetMapping
    public ApiResponse<List<BrandResponse>> getAllBrands(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort
    ) {
        PageResponse<BrandResponse> pageResponse = brandService.getAllBrands(page - 1, size, sort);
        return ApiResponse.<List<BrandResponse>>builder()
                .data(pageResponse.getData())
                .meta(pageResponse.getMeta())
                .message("get all brands successfully")
                .build();
    }

    @GetMapping("/deleted")
    public ApiResponse<List<BrandResponse>> getAllDeletedBrands(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort
    ) {
        PageResponse<BrandResponse> pageResponse = brandService.getAllDeletedBrands(page - 1, size, sort);
        return ApiResponse.<List<BrandResponse>>builder()
                .data(pageResponse.getData())
                .meta(pageResponse.getMeta())
                .message("get all soft deleted brands successfully")
                .build();
    }

    @GetMapping("/{id}/products")
    public ApiResponse<List<ProductResponse>> getAllProductsByBrandId(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort,
            @PathVariable Long id
    ) {
        PageResponse<ProductResponse> pageResponse = brandService.getAllProductsByBrandId(page - 1, size, sort, id);
        return ApiResponse.<List<ProductResponse>>builder()
                .data(pageResponse.getData())
                .meta(pageResponse.getMeta())
                .message("get all soft deleted brands successfully")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<BrandResponse> getBrandDetailById(
            @PathVariable Long id
    ) {
        return ApiResponse.<BrandResponse>builder()
                .data(brandService.getBrandDetailById(id))
                .message("get brand detail by id successfully")
                .build();
    }

    @GetMapping("/slug/{slug}")
    public ApiResponse<BrandResponse> getBrandDetailBySlug(
            @PathVariable String slug
    ) {
        return ApiResponse.<BrandResponse>builder()
                .data(brandService.getBrandDetailBySlug(slug))
                .message("get brand detail by id successfully")
                .build();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<BrandResponse> updateBrand(
            @PathVariable Long id,
            @Valid BrandRequest request,
            @RequestPart(value = "logo", required = false) MultipartFile file
    ) {
        return ApiResponse.<BrandResponse>builder()
                .data(brandService.updateBrand(id, request, file))
                .message("updated brand")
                .build();
    }

    @PatchMapping("/{id}/status")
    public ApiResponse<Void> updateBrandStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        brandService.updateBrandStatus(id, status);
        return ApiResponse.<Void>builder()
                .message("brand status updated successfully")
                .build();
    }

    @DeleteMapping("/{id}/soft-delete")
    public ApiResponse<Void> softDeleteBrand(
            @PathVariable Long id
    ) {
        brandService.softDeleteBrand(id);
        return ApiResponse.<Void>builder()
                .message("soft deleted brand")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteBrand(
            @PathVariable Long id
    ) {
        brandService.deleteBrand(id);
        return ApiResponse.<Void>builder()
                .message("deleted brand")
                .build();
    }

    @PatchMapping("/{id}/restore")
    public ApiResponse<Void> restoreUser(
            @PathVariable Long id
    ) {
        brandService.restoreBrand(id);
        return ApiResponse.<Void>builder()
                .message("brand restored successfully")
                .build();
    }

}
