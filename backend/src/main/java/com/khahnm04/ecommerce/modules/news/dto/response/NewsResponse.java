package com.khahnm04.ecommerce.modules.news.dto.response;

import com.khahnm04.ecommerce.shared.enums.NewsStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class NewsResponse implements Serializable {

    private String id;
    private String title;
    private String slug;
    private String image;
    private String summary;
    private String content;
    private Boolean isFeatured;
    private NewsStatus status;
    private LocalDateTime publishedAt;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime updatedAt;
    private Long updatedBy;
    private LocalDateTime deletedAt;

}
