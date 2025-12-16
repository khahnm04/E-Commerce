package com.khahnm04.ecommerce.entity.news;

import com.khahnm04.ecommerce.common.enums.NewsStatus;
import com.khahnm04.ecommerce.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "news")
public class News extends BaseEntity<Long> {

    @Column(name = "title", nullable = false, length = 500)
    private String title;

    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

    @Column(name = "image", columnDefinition = "TEXT")
    private String image;

    @Column(name = "summary", columnDefinition = "TEXT")
    private String summary;

    @Column(name = "content", nullable = false, columnDefinition = "LONGTEXT")
    private String content;

    @Builder.Default
    @Column(name = "is_featured")
    private Boolean isFeatured = false;

    @Builder.Default
    @ColumnDefault("'PENDING'")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private NewsStatus status = NewsStatus.DRAFT;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

}
