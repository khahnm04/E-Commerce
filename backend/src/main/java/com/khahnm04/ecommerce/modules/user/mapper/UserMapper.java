package com.khahnm04.ecommerce.modules.user.mapper;

import com.khahnm04.ecommerce.modules.user.dto.request.UserProfileRequest;
import com.khahnm04.ecommerce.modules.auth.dto.request.RegisterRequest;
import com.khahnm04.ecommerce.modules.user.dto.request.UserRequest;
import com.khahnm04.ecommerce.modules.user.dto.response.UserProfileResponse;
import com.khahnm04.ecommerce.modules.user.dto.response.UserResponse;
import com.khahnm04.ecommerce.modules.user.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roles", ignore = true)
    User fromUserRequestToUser(UserRequest request);

    @Mapping(target = "roles", ignore = true)
    UserResponse fromUserToUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUser(@MappingTarget User user, UserRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProfile(@MappingTarget User user, UserProfileRequest request);

    UserProfileResponse fromUserToProfileResponse(User user);

    User fromRegisterRequestToUser(RegisterRequest request);

}
