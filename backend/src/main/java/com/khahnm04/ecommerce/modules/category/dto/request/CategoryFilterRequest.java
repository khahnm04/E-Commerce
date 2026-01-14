package com.khahnm04.ecommerce.modules.category.dto.request;

import com.khahnm04.ecommerce.shared.enums.CategoryStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class CategoryFilterRequest {

    private Integer page = 1;
    private Integer size = 10;
    private String sort = "createdAt,desc";

    private Long parentId;
    private CategoryStatus status;
    private String search;

    private String dateType;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

}
