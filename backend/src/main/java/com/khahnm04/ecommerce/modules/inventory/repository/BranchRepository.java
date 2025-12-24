package com.khahnm04.ecommerce.modules.inventory.repository;

import com.khahnm04.ecommerce.shared.enums.BranchStatus;
import com.khahnm04.ecommerce.modules.inventory.entity.Branch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {

    boolean existsByName(String name);

    @Query("select (count(b.id) > 0) from Branch b where upper(b.name) = upper(:name) and b.id != :id")
    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);

    @Query("""
        SELECT b FROM Branch b
        WHERE (:keyword IS NULL OR :keyword = '' OR b.name LIKE %:keyword% OR b.city LIKE %:keyword%)
        AND (:status IS NULL OR b.status = :status)
    """)
    Page<Branch> searchBranches(@Param("keyword") String keyword,
                                @Param("status") BranchStatus status,
                                Pageable pageable);

    List<Branch> findAllByStatus(BranchStatus status);

    List<Branch> findAllByCityAndStatus(String city, BranchStatus status);

}
