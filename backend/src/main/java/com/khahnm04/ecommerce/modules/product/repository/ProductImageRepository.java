package com.khahnm04.ecommerce.modules.product.repository;

import com.khahnm04.ecommerce.modules.product.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
}
