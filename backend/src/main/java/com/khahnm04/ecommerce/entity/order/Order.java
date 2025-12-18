package com.khahnm04.ecommerce.entity.order;

import com.khahnm04.ecommerce.common.enums.OrderStatus;
import com.khahnm04.ecommerce.entity.inventory.Branch;
import com.khahnm04.ecommerce.entity.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_code", nullable = false, unique = true)
    private String orderCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @Column(name = "total_amount", nullable = false)
    private Long totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    // --- Shipping Info ---
    @Column(name = "shipping_name", nullable = false)
    private String shippingName;

    @Column(name = "shipping_phone", nullable = false, length = 15)
    private String shippingPhone;

    @Column(name = "shipping_province", nullable = false)
    private String shippingProvince;

    @Column(name = "shipping_district", nullable = false)
    private String shippingDistrict;

    @Column(name = "shipping_ward", nullable = false)
    private String shippingWard;

    @Column(name = "shipping_address_detail", nullable = false)
    private String shippingAddressDetail;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    @Column(name = "required_date")
    private LocalDate requiredDate;

    @Column(name = "shipped_date")
    private LocalDate shippedDate;

    // --- Audit ---
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // --- Relations ---
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Payment> payments;

}
