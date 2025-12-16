package com.khahnm04.ecommerce.service.user;

import com.khahnm04.ecommerce.dto.request.user.AddressUserRequest;
import com.khahnm04.ecommerce.dto.request.user.ChangePasswordRequest;
import com.khahnm04.ecommerce.dto.request.user.UserProfileRequest;
import com.khahnm04.ecommerce.dto.request.user.UserRequest;
import com.khahnm04.ecommerce.dto.response.user.AddressUserResponse;
import com.khahnm04.ecommerce.dto.response.user.UserProfileResponse;
import com.khahnm04.ecommerce.dto.response.PageResponse;
import com.khahnm04.ecommerce.dto.response.user.UserResponse;
import com.khahnm04.ecommerce.common.enums.UserStatus;
import com.khahnm04.ecommerce.entity.user.User;

import java.util.List;

public interface UserService {

    // admin
    UserResponse createUser(UserRequest request);
    PageResponse<UserResponse> getAllUsers(int page, int size, String sort);
    PageResponse<UserResponse> getAllDeletedUsers(int page, int size, String sort);
    List<AddressUserResponse> getAllAddressesByUserId(Long id);
    UserResponse getUserDetailById(Long id);
    UserResponse updateUser(Long id, UserRequest request);
    void updateUserStatus(Long id, UserStatus status);
    void softDeleteUser(Long id);
    void restoreUser(Long id);

    // client
    User getCurrentUser();
    UserProfileResponse getProfile();
    UserProfileResponse updateProfile(UserProfileRequest request);
    void changePassword(ChangePasswordRequest request);
    AddressUserResponse addAddress(AddressUserRequest request);
    List<AddressUserResponse> getAllAddresses();
    AddressUserResponse updateAddress(Long id, AddressUserRequest request);
    void deleteAddress(Long id);

}
