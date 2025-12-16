package com.khahnm04.ecommerce.dto.response.user;

import com.khahnm04.ecommerce.common.enums.AddressType;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressUserResponse implements Serializable {

    private Long id;
    private String receiverName;
    private String receiverPhone;
    private String province;
    private String district;
    private String ward;
    private String detailAddress;
    private String addressName;
    private AddressType addressType;
    private Boolean isDefault;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime updatedAt;
    private Long updatedBy;
    private LocalDateTime deletedAt;

}
