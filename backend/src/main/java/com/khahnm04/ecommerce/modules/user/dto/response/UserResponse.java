package com.khahnm04.ecommerce.modules.user.dto.response;

import com.khahnm04.ecommerce.shared.enums.Gender;
import com.khahnm04.ecommerce.shared.enums.UserStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse implements Serializable {

    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private Gender gender;
    private UserStatus status;
    private LocalDateTime lastLoginAt;
    private Set<String> roles;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime updatedAt;
    private Long updatedBy;
    private LocalDateTime deletedAt;

}
