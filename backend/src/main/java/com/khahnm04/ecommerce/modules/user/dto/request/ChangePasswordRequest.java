package com.khahnm04.ecommerce.modules.user.dto.request;

import com.khahnm04.ecommerce.shared.validation.password.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {

    @NotBlank(message = "oldPassword cannot be blank")
    @ValidPassword
    private String oldPassword;

    @NotBlank(message = "newPassword cannot be blank")
    @ValidPassword
    private String newPassword;

    @NotBlank(message = "confirmPassword cannot be blank")
    @ValidPassword
    private String confirmPassword;

}
