package com.khahnm04.ecommerce.modules.inventory.repository;

import com.khahnm04.ecommerce.shared.enums.StockMovementType;
import com.khahnm04.ecommerce.modules.inventory.entity.StockMovement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

    // Query phức tạp để xem lịch sử (History) với bộ lọc Dynamic
    // Nếu tham số truyền vào là null thì bỏ qua điều kiện đó
    @Query("""
        SELECT sm FROM StockMovement sm
        JOIN FETCH sm.inventory i
        JOIN FETCH i.productVariant pv
        JOIN FETCH pv.product p
        JOIN FETCH i.branch b
        WHERE (:inventoryId IS NULL OR i.id = :inventoryId)
        AND (:branchId IS NULL OR b.id = :branchId)
        AND (:productId IS NULL OR p.id = :productId)
        AND (:variantId IS NULL OR pv.id = :variantId)
        AND (:type IS NULL OR sm.type = :type)
    """)
    Page<StockMovement> findWithFilters(
            @Param("inventoryId") Long inventoryId,
            @Param("branchId") Long branchId,
            @Param("productId") Long productId,
            @Param("variantId") Long variantId,
            @Param("type") StockMovementType type,
            Pageable pageable
    );

}
