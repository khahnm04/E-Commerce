package com.khahnm04.ecommerce.entity.product;

import com.khahnm04.ecommerce.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "attributes")
public class Attribute extends BaseEntity<Long> {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

//    @OneToMany(mappedBy = "attribute", fetch = FetchType.LAZY)
//    private List<ProductAttributeValue> productAttributeValues;

}
