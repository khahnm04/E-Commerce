package com.khahnm04.ecommerce.repository;

import com.khahnm04.ecommerce.entity.product.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
}
