package com.khahnm04.ecommerce.dto.response.inventory;

import com.khahnm04.ecommerce.common.enums.BranchStatus;
import com.khahnm04.ecommerce.dto.response.BaseResponse;
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
