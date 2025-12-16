package com.khahnm04.ecommerce.service.auth.permission;

import com.khahnm04.ecommerce.dto.response.auth.PermissionResponse;
import com.khahnm04.ecommerce.entity.auth.Permission;
import com.khahnm04.ecommerce.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    @Override
    public List<PermissionResponse> getAllPermissions() {
        List<Permission> permissions = permissionRepository.findAll();
        return permissions.stream()
                .map(permission -> PermissionResponse.builder()
                            .name(permission.getName())
                            .description(permission.getDescription())
                            .build())
                .toList();
    }

}
