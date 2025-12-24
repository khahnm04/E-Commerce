package com.khahnm04.ecommerce.modules.product.entity;

import com.khahnm04.ecommerce.shared.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "variant_values")
public class VariantValue extends BaseEntity<Long> {

    @Column(name = "value", nullable = false)
    private String value;

    @Column(name = "code")
    private String code;

    @Column(name = "display_order")
    @Builder.Default
    private Integer displayOrder = 0;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "variant_id", nullable = false)
    private Variant variant;

}
