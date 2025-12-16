package com.khahnm04.ecommerce.entity.product;

import com.khahnm04.ecommerce.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_variants")
public class ProductVariant extends BaseEntity<Long> {

    @Column(name = "sku", nullable = false, unique = true)
    private String sku;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "old_price")
    private Long oldPrice;

    @Column(name = "image", columnDefinition = "TEXT")
    private String image;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Builder.Default
    @OneToMany(mappedBy = "productVariant", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductVariantValue> productVariantValues = new ArrayList<>();

    public void addVariantValue(ProductVariantValue value) {
        productVariantValues.add(value);
        value.setProductVariant(this);
    }

}
