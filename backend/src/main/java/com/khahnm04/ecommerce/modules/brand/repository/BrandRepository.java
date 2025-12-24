package com.khahnm04.ecommerce.modules.brand.repository;

import com.khahnm04.ecommerce.modules.brand.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    boolean existsBySlug(String slug);

    boolean existsByName(String name);

    Page<Brand> findAllByDeletedAtIsNull(Pageable pageable);

    Page<Brand> findAllByDeletedAtIsNotNull(Pageable pageable);

    Optional<Brand> findBySlug(String slug);

}
