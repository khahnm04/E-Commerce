package com.khahnm04.ecommerce.modules.auth.mapper;

import com.khahnm04.ecommerce.modules.auth.dto.request.RoleRequest;
import com.khahnm04.ecommerce.modules.auth.dto.response.RoleResponse;
import com.khahnm04.ecommerce.modules.auth.entity.Role;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);

    @Mapping(target = "name", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRole(@MappingTarget Role role, RoleRequest request);

}
