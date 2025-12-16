package com.khahnm04.ecommerce.entity.product;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
    name = "product_variant_values",
    uniqueConstraints = @UniqueConstraint(columnNames = {"product_variant_id", "variant_value_id"})
)
@EntityListeners(AuditingEntityListener.class)
public class ProductVariantValue implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_variant_id", nullable = false)
    @ToString.Exclude
    private ProductVariant productVariant;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "variant_value_id", nullable = false)
    private VariantValue variantValue;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(name = "created_by")
    private Long createdBy;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductVariantValue that = (ProductVariantValue) o;
        return getId() != null && getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
