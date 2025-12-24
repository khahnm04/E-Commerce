package com.khahnm04.ecommerce.modules.order.dto.request;

import com.khahnm04.ecommerce.shared.enums.PaymentMethod;
import com.khahnm04.ecommerce.shared.validation.phone.ValidPhoneNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @NotNull(message = "Chi nhánh không được để trống")
    private Long branchId; // Mua hàng từ kho nào?

    @NotBlank(message = "Tên người nhận không được để trống")
    private String shippingName;

    @NotBlank(message = "Số điện thoại không được để trống")
    @ValidPhoneNumber
    private String shippingPhone;

    @NotBlank(message = "Tỉnh/Thành phố không được để trống")
    private String shippingProvince;

    @NotBlank(message = "Quận/Huyện không được để trống")
    private String shippingDistrict;

    @NotBlank(message = "Phường/Xã không được để trống")
    private String shippingWard;

    @NotBlank(message = "Địa chỉ chi tiết không được để trống")
    private String shippingAddressDetail;

    private String note; // Ghi chú (VD: Giao giờ hành chính)

    private String couponCode; // Mã giảm giá (Optional)

    @NotNull(message = "Phương thức thanh toán không được để trống")
    private PaymentMethod paymentMethod;

}
