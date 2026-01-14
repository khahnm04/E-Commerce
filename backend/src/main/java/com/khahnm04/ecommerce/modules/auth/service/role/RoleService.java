package com.khahnm04.ecommerce.modules.auth.service.role;

import com.khahnm04.ecommerce.modules.auth.dto.request.RoleRequest;
import com.khahnm04.ecommerce.modules.auth.dto.response.RoleResponse;
import java.util.List;

public interface RoleService {

    RoleResponse createRole(RoleRequest request);
    List<RoleResponse> getAllRoles();
    RoleResponse updateRole(Long id, RoleRequest request);
    void deleteRole(Long id);

}
