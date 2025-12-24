package com.khahnm04.ecommerce.modules.order.entity;

import com.khahnm04.ecommerce.modules.product.entity.ProductVariant;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_details", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"order_id", "product_variant_id"})
})
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variant_id", nullable = false)
    private ProductVariant productVariant;

    @Column(name = "unit_price", nullable = false)
    private Long unitPrice;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    // Feedback (1-1 với OrderDetail)
    @OneToOne(mappedBy = "orderDetail", cascade = CascadeType.ALL)
    private Feedback feedback;

}
