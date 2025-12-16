package com.khahnm04.ecommerce.dto.response.auth;

import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionResponse implements Serializable {

    private String name;
    private String description;

}
