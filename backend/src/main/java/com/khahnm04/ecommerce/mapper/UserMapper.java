package com.khahnm04.ecommerce.mapper;

import com.khahnm04.ecommerce.dto.request.user.UserProfileRequest;
import com.khahnm04.ecommerce.dto.request.auth.RegisterRequest;
import com.khahnm04.ecommerce.dto.request.user.UserRequest;
import com.khahnm04.ecommerce.dto.response.user.UserProfileResponse;
import com.khahnm04.ecommerce.dto.response.user.UserResponse;
import com.khahnm04.ecommerce.entity.user.User;
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
