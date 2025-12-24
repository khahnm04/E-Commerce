package com.khahnm04.ecommerce.modules.auth.service.role;

import com.khahnm04.ecommerce.modules.auth.dto.request.RoleRequest;
import com.khahnm04.ecommerce.modules.auth.dto.response.RoleResponse;
import com.khahnm04.ecommerce.modules.auth.entity.Permission;
import com.khahnm04.ecommerce.modules.auth.entity.Role;
import com.khahnm04.ecommerce.core.exception.AppException;
import com.khahnm04.ecommerce.core.exception.ErrorCode;
import com.khahnm04.ecommerce.modules.auth.mapper.RoleMapper;
import com.khahnm04.ecommerce.modules.auth.repository.PermissionRepository;
import com.khahnm04.ecommerce.modules.auth.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleResponse createRole(RoleRequest request) {
        if (roleRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.ROLE_EXISTED);
        }
        Role role = roleMapper.toRole(request);
        assignPermissionsToRole(role, request.getPermissions());
        role = roleRepository.save(role);
        log.info("UserRole created with name = {}", role.getName());
        return roleMapper.toRoleResponse(role);
    }

    @Override
    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toRoleResponse)
                .toList();
    }

    @Override
    public RoleResponse updateRole(String name, RoleRequest request) {
        Role role = getRoleById(name);
        roleMapper.updateRole(role, request);
        assignPermissionsToRole(role, request.getPermissions());
        role = roleRepository.save(role);
        log.info("UserRole updated successfully with id = {}", name);
        return roleMapper.toRoleResponse(role);
    }

    @Override
    public void deleteRole(String name) {
        roleRepository.delete(getRoleById(name));
        log.info("UserRole deleted successfully with name: {}", name);
    }

    private Role getRoleById(String name) {
        return roleRepository.findById(name)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
    }

    private void assignPermissionsToRole(Role role, Set<String> permissionNames) {
        Set<Permission> permissions = Optional.ofNullable(permissionNames)
                .orElseGet(Collections::emptySet)
                .stream()
                .map(permissionId -> permissionRepository.findById(permissionId)
                        .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND)))
                .collect(Collectors.toSet());
        role.setPermissions(permissions);
    }

}
