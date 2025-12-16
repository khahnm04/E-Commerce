package com.khahnm04.ecommerce.service.auth.role;

import com.khahnm04.ecommerce.dto.request.auth.RoleRequest;
import com.khahnm04.ecommerce.dto.response.auth.RoleResponse;
import java.util.List;

public interface RoleService {

    RoleResponse createRole(RoleRequest request);
    List<RoleResponse> getAllRoles();
    RoleResponse updateRole(String id, RoleRequest request);
    void deleteRole(String name);

}
