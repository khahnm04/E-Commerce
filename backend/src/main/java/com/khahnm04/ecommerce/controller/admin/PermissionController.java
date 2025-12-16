package com.khahnm04.ecommerce.controller.admin;

import com.khahnm04.ecommerce.dto.response.ApiResponse;
import com.khahnm04.ecommerce.dto.response.auth.PermissionResponse;
import com.khahnm04.ecommerce.service.auth.permission.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("${api.prefix}/admin/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @GetMapping
    ApiResponse<List<PermissionResponse>> getAllPermissions() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .data(permissionService.getAllPermissions())
                .message("get all permissions successfully")
                .build();
    }

}
