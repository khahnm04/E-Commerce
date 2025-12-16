package com.khahnm04.ecommerce.entity.brand;

import com.khahnm04.ecommerce.common.enums.BrandStatus;
import com.khahnm04.ecommerce.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "brands")
public class Brand extends BaseEntity<Long> {

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "logo", columnDefinition = "TEXT")
    private String logo;

    @Column(name = "country")
    private String country;

    @Builder.Default
    @ColumnDefault("'ACTIVE'")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BrandStatus status = BrandStatus.ACTIVE;

}
