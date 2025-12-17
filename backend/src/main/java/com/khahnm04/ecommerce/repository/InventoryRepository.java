package com.khahnm04.ecommerce.repository;

import com.khahnm04.ecommerce.entity.inventory.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Query("select i from Inventory i where i.productVariant.id = :variantId and i.branch.id = :branchId")
    Optional<Inventory> findByProductVariantIdAndBranchId(Long variantId, Long branchId);

    @Query("SELECT COALESCE(SUM(i.quantity), 0) FROM Inventory i WHERE i.productVariant.id = :variantId")
    Long sumQuantityByVariantId(Long variantId);

    @Query("select i from Inventory i where i.productVariant.id = :variantId")
    List<Inventory> findByProductVariantId(Long variantId);

}
