package com.khahnm04.ecommerce.repository;

import com.khahnm04.ecommerce.entity.product.VariantValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VariantValueRepository extends JpaRepository<VariantValue, Long> {

    boolean existsByVariantIdAndValue(Long variantId, String value);

    Page<VariantValue> findAllByDeletedAtIsNull(Pageable pageable);

    Page<VariantValue> findAllByDeletedAtIsNotNull(Pageable pageable);

    // Tìm kiếm các giá trị của một biến thể cụ thể (VD: Lấy tất cả màu sắc)
    Page<VariantValue> findAllByVariantIdAndDeletedAtIsNull(Long variantId, Pageable pageable);

    @Query("SELECT (count(vv.id) > 0) FROM VariantValue vv WHERE vv.variant.id = :variantId AND UPPER(vv.value) = UPPER(:value) AND vv.id != :id")
    boolean existsByVariantIdAndValueIgnoreCaseAndIdNot(@Param("variantId") Long variantId, @Param("value") String value, @Param("id") Long id);

}
