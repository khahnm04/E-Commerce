package com.khahnm04.ecommerce.repository;

import com.khahnm04.ecommerce.entity.inventory.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    // 1. Tìm Inventory cụ thể theo Variant và Branch (Dùng khi nhập/xuất kho)
    @Query("select i from Inventory i where i.productVariant.id = :variantId and i.branch.id = :branchId")
    Optional<Inventory> findByProductVariantIdAndBranchId(@Param("variantId") Long variantId, @Param("branchId") Long branchId);

    // 2. Lấy danh sách các kho đang giữ biến thể này (Dùng cho ProductVariantController)
    @Query("select i from Inventory i join fetch i.branch where i.productVariant.id = :variantId")
    List<Inventory> findByProductVariantId(@Param("variantId") Long variantId);

    // 3. Tính tổng tồn kho của biến thể trên toàn hệ thống (Dùng hiển thị cho khách hàng)
    @Query("SELECT COALESCE(SUM(i.quantity), 0) FROM Inventory i WHERE i.productVariant.id = :variantId")
    Long sumQuantityByVariantId(@Param("variantId") Long variantId);

    // 4. (MỚI) Trừ kho AN TOÀN (Atomic Update)
    // Chỉ trừ khi số lượng tồn >= số lượng trừ. Trả về 1 nếu thành công, 0 nếu thất bại (hết hàng).
    @Modifying
    @Query("""
        UPDATE Inventory i SET i.quantity = i.quantity - :quantity
        WHERE i.productVariant.id = :variantId
        AND i.branch.id = :branchId
        AND i.quantity >= :quantity
    """)
    int reduceStock(@Param("variantId") Long variantId,
                    @Param("branchId") Long branchId,
                    @Param("quantity") Long quantity);

    // 5. (MỚI) Tìm kiếm và Lọc Inventory (Dùng cho trang quản lý kho chung - InventoryController)
    // - keyword: Tìm theo Tên sản phẩm hoặc SKU
    // - branchId: Lọc theo chi nhánh
    // - lowStockThreshold: Lọc các sản phẩm có số lượng <= ngưỡng này (Ví dụ: <= 10)
    @Query("""
        SELECT i FROM Inventory i
        JOIN FETCH i.productVariant pv
        JOIN FETCH pv.product p
        JOIN FETCH i.branch b
        WHERE (:branchId IS NULL OR b.id = :branchId)
        AND (:keyword IS NULL OR (lower(p.name) LIKE lower(concat('%', :keyword, '%')) OR lower(pv.sku) LIKE lower(concat('%', :keyword, '%'))))
        AND (:lowStockThreshold IS NULL OR i.quantity <= :lowStockThreshold)
    """)
    Page<Inventory> findWithFilters(
            @Param("branchId") Long branchId,
            @Param("keyword") String keyword,
            @Param("lowStockThreshold") Long lowStockThreshold,
            Pageable pageable
    );

}
