package com.khahnm04.ecommerce.modules.auth.dto.request;

import com.khahnm04.ecommerce.shared.validation.email.ValidEmail;
import com.khahnm04.ecommerce.shared.validation.password.ValidPassword;
import com.khahnm04.ecommerce.shared.validation.phone.ValidPhoneNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest implements Serializable {

    @NotBlank(message = "full name is not blank")
    private String fullName;

    @NotNull(message = "dateOfBirth cannot be null")
    private LocalDate dateOfBirth;

    @ValidPhoneNumber
    @NotBlank(message = "phoneNumber cannot be blank")
    private String phoneNumber;

    @ValidEmail
    private String email;

    @ValidPassword
    @NotBlank(message = "password cannot be blank")
    private String password;

    @NotBlank(message = "confirmPassword cannot be blank")
    private String confirmPassword;

}
