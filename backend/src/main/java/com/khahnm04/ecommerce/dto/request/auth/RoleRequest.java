package com.khahnm04.ecommerce.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequest implements Serializable {

    @NotBlank(message = "name cannot be blank")
    private String name;

    @NotBlank(message = "description cannot be blank")
    private String description;

    @NotEmpty(message = "permissions cannot be blank")
    private Set<String> permissions;

}
