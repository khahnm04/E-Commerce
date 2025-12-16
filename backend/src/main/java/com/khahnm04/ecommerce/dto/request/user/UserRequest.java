package com.khahnm04.ecommerce.dto.request.user;

import com.khahnm04.ecommerce.common.enums.Gender;
import com.khahnm04.ecommerce.common.enums.UserStatus;
import com.khahnm04.ecommerce.common.validation.email.ValidEmail;
import com.khahnm04.ecommerce.common.validation.enums.ValidEnum;
import com.khahnm04.ecommerce.common.validation.password.ValidPassword;
import com.khahnm04.ecommerce.common.validation.phone.ValidPhoneNumber;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest implements Serializable {

    @ValidEmail
    private String email;

    @ValidPhoneNumber
    @NotBlank(message = "phoneNumber cannot be blank")
    private String phoneNumber;

    @ValidPassword
    @NotBlank(message = "password cannot be blank")
    private String password;

    @NotBlank(message = "fullName cannot be blank")
    private String fullName;

    private LocalDate dateOfBirth;

    @ValidEnum(name = "gender", enumClass = Gender.class)
    private String gender;

    @ValidEnum(name = "status", enumClass = UserStatus.class)
    private String status;

    private Set<String> roles;

}
