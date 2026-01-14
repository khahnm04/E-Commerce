package com.khahnm04.ecommerce.modules.coupon.dto.request;

import com.khahnm04.ecommerce.shared.enums.DiscountType;
import com.khahnm04.ecommerce.shared.validation.enums.ValidEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponRequest {

    @NotBlank(message = "Coupon code is required")
    private String code;

    @ValidEnum(name = "discountType", enumClass = DiscountType.class)
    private String discountType;

    @NotNull(message = "Discount value is required")
    @Min(value = 0, message = "Discount value must be > 0")
    private BigDecimal discountValue;

    @Min(value = 0, message = "Min order value must be >= 0")
    private BigDecimal minOrderValue;

    @Min(value = 1, message = "Max usage must be >= 1")
    private Integer maxUsage;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

}
