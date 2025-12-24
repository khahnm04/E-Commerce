package com.khahnm04.ecommerce.modules.product.repository;

import com.khahnm04.ecommerce.modules.product.entity.Variant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface VariantRepository extends JpaRepository<Variant, Long> {

    boolean existsByCode(String code);

    Page<Variant> findAllByDeletedAtIsNull(Pageable pageable);

    Page<Variant> findAllByDeletedAtIsNotNull(Pageable pageable);

    Optional<Variant> findByCode(String code);

    @Query("SELECT (count(v.id) > 0) FROM Variant v WHERE UPPER(v.code) = UPPER(:code) AND v.id != :id")
    boolean existsByCodeIgnoreCaseAndIdNot(@Param("code") String code, @Param("id") Long id);

}
