package com.khahnm04.ecommerce.entity.inventory;

import com.khahnm04.ecommerce.entity.BaseEntity;
import com.khahnm04.ecommerce.entity.product.ProductVariant;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "inventories", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"product_variant_id", "branch_id"})
})
public class Inventory extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variant_id", nullable = false)
    private ProductVariant productVariant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @Builder.Default
    @Column(name = "quantity", nullable = false)
    private Long quantity = 0L;

}
