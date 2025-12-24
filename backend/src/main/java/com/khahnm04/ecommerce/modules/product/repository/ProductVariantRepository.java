package com.khahnm04.ecommerce.modules.product.repository;

import com.khahnm04.ecommerce.modules.product.entity.ProductVariant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {

    boolean existsBySku(String sku);

    Optional<ProductVariant> findBySku(String sku);

    Page<ProductVariant> findAllByDeletedAtIsNull(Pageable pageable);

    Page<ProductVariant> findAllByDeletedAtIsNotNull(Pageable pageable);

    @Query("select p from ProductVariant p where p.product.id = :productId and p.deletedAt is null")
    Page<ProductVariant> findAllByProductIdAndDeletedAtIsNull(Long productId, Pageable pageable);

    @Query("SELECT (count(pv.id) > 0) FROM ProductVariant pv WHERE UPPER(pv.sku) = UPPER(:sku) AND pv.id != :id")
    boolean existsBySkuIgnoreCaseAndIdNot(@Param("sku") String sku, @Param("id") Long id);

}
