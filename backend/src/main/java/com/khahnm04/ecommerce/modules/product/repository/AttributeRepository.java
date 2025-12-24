package com.khahnm04.ecommerce.modules.product.repository;

import com.khahnm04.ecommerce.modules.product.entity.Attribute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Long> {

    boolean existsByCode(String code);

    Page<Attribute> findAllByDeletedAtIsNull(Pageable pageable);

    Page<Attribute> findAllByDeletedAtIsNotNull(Pageable pageable);

    Optional<Attribute> findByCode(String code);

    @Query("SELECT (count(a.id) > 0) FROM Attribute a WHERE UPPER(a.code) = UPPER(:code) AND a.id != :id")
    boolean existsByCodeIgnoreCaseAndIdNot(@Param("code") String code, @Param("id") Long id);

}
