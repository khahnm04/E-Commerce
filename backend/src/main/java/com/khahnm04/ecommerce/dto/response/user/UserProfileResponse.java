package com.khahnm04.ecommerce.dto.response.user;

import com.khahnm04.ecommerce.common.enums.Gender;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse implements Serializable {

    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private Gender gender;

}
