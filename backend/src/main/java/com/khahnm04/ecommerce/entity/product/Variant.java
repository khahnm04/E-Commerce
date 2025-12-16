package com.khahnm04.ecommerce.entity.product;

import com.khahnm04.ecommerce.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "variants")
public class Variant extends BaseEntity<Long> {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @OneToMany(mappedBy = "variant", fetch = FetchType.LAZY)
    private List<VariantValue> variantValues;

}
