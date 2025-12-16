package com.khahnm04.ecommerce.dto.request.user;

import com.khahnm04.ecommerce.common.enums.AddressType;
import com.khahnm04.ecommerce.common.validation.enums.ValidEnum;
import com.khahnm04.ecommerce.common.validation.phone.ValidPhoneNumber;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressUserRequest {

    @NotBlank(message = "receiverName cannot be blank")
    private String receiverName;

    @ValidPhoneNumber
    @NotBlank(message = "receiverPhone cannot be blank")
    private String receiverPhone;

    @NotBlank(message = "province cannot be blank")
    private String province;

    @NotBlank(message = "district cannot be blank")
    private String district;

    @NotBlank(message = "ward cannot be blank")
    private String ward;

    @NotBlank(message = "detailAddress cannot be blank")
    private String detailAddress;

    private String addressName;

    @ValidEnum(name = "addressType", enumClass = AddressType.class)
    private String addressType;

    private Boolean isDefault;

}
