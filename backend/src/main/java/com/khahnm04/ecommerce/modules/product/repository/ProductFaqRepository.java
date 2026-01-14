package com.khahnm04.ecommerce.modules.product.repository;

import com.khahnm04.ecommerce.modules.product.entity.ProductFaq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductFaqRepository extends JpaRepository<ProductFaq, Long> {

    @Query("select p from ProductFaq p where p.product.id = ?1 and p.deletedAt is null")
    Page<ProductFaq> findAllByProductIdAndDeletedAtIsNull(Long productId, Pageable pageable);

}
