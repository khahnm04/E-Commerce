package com.khahnm04.ecommerce.modules.auth.dto.response;

import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse implements Serializable {

    private Long id;
    private String fullName;

}
