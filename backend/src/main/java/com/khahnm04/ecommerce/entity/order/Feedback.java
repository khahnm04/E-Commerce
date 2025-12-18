package com.khahnm04.ecommerce.entity.order;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "feedbacks")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_detail_id", nullable = false, unique = true)
    private OrderDetail orderDetail;

    @Column(name = "rating", nullable = false, columnDefinition = "TINYINT CHECK (rating BETWEEN 1 AND 5)")
    private Integer rating;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "images", columnDefinition = "JSON")
    private String images; // Lưu chuỗi JSON

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

}
