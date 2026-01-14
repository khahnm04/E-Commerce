package com.khahnm04.ecommerce.modules.product.service.impl;

import com.khahnm04.ecommerce.modules.product.dto.request.ProductFaqRequest;
import com.khahnm04.ecommerce.modules.product.dto.response.ProductFaqResponse;
import com.khahnm04.ecommerce.modules.product.entity.Product;
import com.khahnm04.ecommerce.modules.product.entity.ProductFaq;
import com.khahnm04.ecommerce.core.exception.AppException;
import com.khahnm04.ecommerce.core.exception.ErrorCode;
import com.khahnm04.ecommerce.modules.product.mapper.ProductFaqMapper;
import com.khahnm04.ecommerce.modules.product.repository.ProductFaqRepository;
import com.khahnm04.ecommerce.modules.product.repository.ProductRepository;
import com.khahnm04.ecommerce.modules.product.service.ProductFaqService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductFaqServiceImpl implements ProductFaqService {

    private final ProductFaqRepository productFaqRepository;
    private final ProductRepository productRepository;
    private final ProductFaqMapper productFaqMapper;

    @Override
    @Transactional
    public ProductFaqResponse createProductFaq(ProductFaqRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        ProductFaq productFaq = productFaqMapper.fromRequestToEntity(request);
        productFaq.setProduct(product);

        if (productFaq.getDisplayOrder() == null) {
            productFaq.setDisplayOrder(0);
        }

        ProductFaq savedFaq = productFaqRepository.save(productFaq);
        log.info("Product FAQ created with id = {}", savedFaq.getId());
        return productFaqMapper.toResponse(savedFaq);
    }

    @Override
    public ProductFaqResponse getDetailById(Long id) {
        return productFaqMapper.toResponse(getById(id));
    }

    @Override
    @Transactional
    public ProductFaqResponse updateProductFaq(Long id, ProductFaqRequest request) {
        ProductFaq productFaq = getById(id);

        if (request.getProductId() != null && !request.getProductId().equals(productFaq.getProduct().getId())) {
            Product newProduct = productRepository.findById(request.getProductId())
                    .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
            productFaq.setProduct(newProduct);
        }

        productFaqMapper.updateEntity(productFaq, request);
        ProductFaq savedFaq = productFaqRepository.save(productFaq);
        log.info("Product FAQ updated with id = {}", savedFaq.getId());
        return productFaqMapper.toResponse(savedFaq);
    }

    @Override
    public void softDeleteProductFaq(Long id) {
        ProductFaq faq = getById(id);
        if (faq.getDeletedAt() != null) {
            throw new AppException(ErrorCode.PRODUCT_FAQ_ALREADY_SOFT_DELETED);
        }
        faq.setDeletedAt(LocalDateTime.now());
        productFaqRepository.save(faq);
        log.info("Product FAQ soft deleted with id = {}", id);
    }

    @Override
    public void deleteProductFaq(Long id) {
        ProductFaq faq = getById(id);
        productFaqRepository.delete(faq);
        log.info("Product FAQ hard deleted with id = {}", id);
    }

    @Override
    public void restoreProductFaq(Long id) {
        ProductFaq faq = getById(id);
        faq.setDeletedAt(null);
        productFaqRepository.save(faq);
        log.info("Product FAQ restored with id = {}", id);
    }

    private ProductFaq getById(Long id) {
        return productFaqRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_FAQ_NOT_FOUND));
    }

}
