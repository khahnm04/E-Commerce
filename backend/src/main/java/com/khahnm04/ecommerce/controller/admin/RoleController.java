package com.khahnm04.ecommerce.controller.admin;

import com.khahnm04.ecommerce.dto.request.auth.RoleRequest;
import com.khahnm04.ecommerce.dto.response.ApiResponse;
import com.khahnm04.ecommerce.dto.response.auth.RoleResponse;
import com.khahnm04.ecommerce.service.auth.role.RoleServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("${api.prefix}/admin/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleServiceImpl roleService;

    @PostMapping
    ApiResponse<RoleResponse> createRole(
        @Valid @RequestBody RoleRequest request
    ) {
        return ApiResponse.<RoleResponse>builder()
                .data(roleService.createRole(request))
                .message("role created successfully")
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> getAllRoles() {
        return ApiResponse.<List<RoleResponse>>builder()
                .data(roleService.getAllRoles())
                .message("get all roles successfully")
                .build();
    }

    @PutMapping("/{name}")
    ApiResponse<RoleResponse> updateRole(
        @PathVariable String name,
        @Valid @RequestBody RoleRequest request
    ) {
        return ApiResponse.<RoleResponse>builder()
                .data(roleService.updateRole(name, request))
                .message("role updated successfully")
                .build();
    }

    @DeleteMapping("/{name}")
    ApiResponse<Void> deleteRole(
        @PathVariable String name
    ) {
        roleService.deleteRole(name);
        return ApiResponse.<Void>builder()
                .message("role deleted successfully")
                .build();
    }

}
