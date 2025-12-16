package com.khahnm04.ecommerce.dto.response.auth;

import lombok.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse implements Serializable {

    private String name;
    private String description;
    private Set<PermissionResponse> permissions;

}
