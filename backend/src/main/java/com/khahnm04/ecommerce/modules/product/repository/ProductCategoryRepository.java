package com.khahnm04.ecommerce.modules.product.repository;

import com.khahnm04.ecommerce.modules.product.entity.ProductCategory;
import com.khahnm04.ecommerce.modules.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Set;
import java.util.List;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    @Query("""
        SELECT p
        FROM ProductCategory pc
        JOIN pc.product p
        WHERE pc.category.id = :categoryId
    """)
    Page<Product> findAllByCategoryId(@Param("categoryId") Long id, Pageable pageable);

    @Query("""
        SELECT pc.product.id
        FROM ProductCategory pc
        WHERE pc.category.id = :categoryId
        AND pc.product.id IN :productIds
    """)
    Set<Long> findExistingProductIds(@Param("categoryId") Long categoryId,
                                     @Param("productIds") Set<Long> productIds);

    @Query("select (count(p.id) > 0) from ProductCategory p where p.category.id in :categoryIds")
    boolean existsByCategoryIdIn(List<Long> categoryIds);

}
