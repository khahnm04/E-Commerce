package com.khahnm04.ecommerce.modules.inventory.dto.response;

import com.khahnm04.ecommerce.shared.enums.BranchStatus;
import com.khahnm04.ecommerce.shared.dto.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BranchResponse extends BaseResponse<Long> {

    private String name;
    private String address;
    private String city;
    private String phoneNumber;
    private BranchStatus status;

}
