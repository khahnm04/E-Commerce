package com.khahnm04.ecommerce.modules.inventory.dto.request;

import com.khahnm04.ecommerce.shared.enums.BranchStatus;
import com.khahnm04.ecommerce.shared.validation.enums.ValidEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchRequest implements Serializable {

    @NotBlank(message = "name cannot be blank")
    private String name;

    @NotBlank(message = "address cannot be blank")
    private String address;

    @NotBlank(message = "city cannot be blank")
    private String city;

    @NotBlank(message = "phoneNumber cannot be blank")
    private String phoneNumber;

    @ValidEnum(name = "status", enumClass = BranchStatus.class)
    private String status;

}
