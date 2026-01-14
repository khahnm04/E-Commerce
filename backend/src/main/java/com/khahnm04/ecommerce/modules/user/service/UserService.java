package com.khahnm04.ecommerce.modules.user.service;

import com.khahnm04.ecommerce.modules.user.dto.request.AddressUserRequest;
import com.khahnm04.ecommerce.modules.user.dto.request.ChangePasswordRequest;
import com.khahnm04.ecommerce.modules.user.dto.request.UserProfileRequest;
import com.khahnm04.ecommerce.modules.user.dto.request.UserRequest;
import com.khahnm04.ecommerce.modules.user.dto.response.AddressUserResponse;
import com.khahnm04.ecommerce.modules.user.dto.response.UserProfileResponse;
import com.khahnm04.ecommerce.shared.dto.PageResponse;
import com.khahnm04.ecommerce.modules.user.dto.response.UserResponse;
import com.khahnm04.ecommerce.shared.enums.UserStatus;
import com.khahnm04.ecommerce.modules.user.entity.User;
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
