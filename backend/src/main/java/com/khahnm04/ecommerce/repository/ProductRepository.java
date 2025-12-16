package com.khahnm04.ecommerce.repository;

import com.khahnm04.ecommerce.entity.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsBySlug(String slug);

    boolean existsByName(String name);

    Page<Product> findAllByDeletedAtIsNull(Pageable pageable);

    Page<Product> findAllByDeletedAtIsNotNull(Pageable pageable);

    Optional<Product> findBySlug(String slug);

    @Query("SELECT p FROM Product p WHERE p.brand.id = :brandId")
    Page<Product> findAllByBrandId(@Param("brandId") Long brandId, Pageable pageable);

    @Query("SELECT (count(p.id) > 0) FROM Product p WHERE UPPER(p.slug) = UPPER(:slug) AND p.id != :id")
    boolean existsBySludIgnoreCaseAndIdNot(@Param("slug") String slug, @Param("id") Long id);

}
