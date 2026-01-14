package com.khahnm04.ecommerce.modules.coupon.dto.response;

import com.khahnm04.ecommerce.shared.enums.DiscountType;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponResponse {

    private Long id;
    private String code;
    private DiscountType discountType;
    private BigDecimal discountValue;
    private BigDecimal minOrderValue;
    private Integer maxUsage;
    private Integer usageCount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean active;

}
