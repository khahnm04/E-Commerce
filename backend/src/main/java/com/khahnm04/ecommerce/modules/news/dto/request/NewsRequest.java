package com.khahnm04.ecommerce.modules.news.dto.request;

import com.khahnm04.ecommerce.shared.enums.NewsStatus;
import com.khahnm04.ecommerce.shared.validation.enums.ValidEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsRequest {

    @NotBlank(message = "title cannot be blank")
    private String title;

    @NotBlank(message = "slug cannot be blank")
    private String slug;

    private MultipartFile image;

    private String summary;

    @NotBlank(message = "content cannot be blank")
    private String content;

    @Builder.Default
    private Boolean isFeatured = false;

    @ValidEnum(name = "status", enumClass = NewsStatus.class)
    private String status;

    private LocalDateTime publishedAt;

}
