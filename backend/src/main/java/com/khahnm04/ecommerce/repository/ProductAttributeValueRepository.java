package com.khahnm04.ecommerce.repository;

import com.khahnm04.ecommerce.entity.product.ProductAttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductAttributeValueRepository extends JpaRepository<ProductAttributeValue, Long> {

    @Query("select (count(p) > 0) from ProductAttributeValue p where p.product.id = :productId and p.attribute.id = :attributeId")
    boolean existsByProductIdAndAttributeId(Long productId, Long attributeId);

    @Query("select p from ProductAttributeValue p where p.product.id = :productId")
    List<ProductAttributeValue> findAllByProductId(Long productId);

}
