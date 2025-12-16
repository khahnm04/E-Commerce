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
@Table(name = "product_faqs")
public class ProductFaq extends BaseEntity<Long> {

    @Column(name = "question", length = 500, nullable = false)
    private String question;

    @Column(name = "answer", columnDefinition = "TEXT", nullable = false)
    private String answer;

    @Builder.Default
    @Column(name = "display_order")
    private Integer displayOrder = 0;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}
