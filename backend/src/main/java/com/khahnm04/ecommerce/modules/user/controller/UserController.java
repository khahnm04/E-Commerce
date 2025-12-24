package com.khahnm04.ecommerce.modules.user.controller;

import com.khahnm04.ecommerce.modules.user.dto.request.AddressUserRequest;
import com.khahnm04.ecommerce.modules.user.dto.request.ChangePasswordRequest;
import com.khahnm04.ecommerce.modules.user.dto.request.UserProfileRequest;
import com.khahnm04.ecommerce.modules.user.dto.response.AddressUserResponse;
import com.khahnm04.ecommerce.modules.user.dto.response.UserProfileResponse;
import com.khahnm04.ecommerce.modules.user.service.UserService;
import com.khahnm04.ecommerce.shared.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ApiResponse<UserProfileResponse> getProfile() {
        return ApiResponse.<UserProfileResponse>builder()
                .data(userService.getProfile())
                .message("get my profile successfully")
                .build();
    }

    @PutMapping("/profile/update")
    public ApiResponse<UserProfileResponse> updateProfile(
        @Valid @RequestBody UserProfileRequest request
    ) {
        return ApiResponse.<UserProfileResponse>builder()
                .data(userService.updateProfile(request))
                .message("updated my profile successfully")
                .build();
    }

    @PatchMapping("/profile/change-password")
    public ApiResponse<Void> changePassword(
            @Valid @RequestBody ChangePasswordRequest request
    ) {
        userService.changePassword(request);
        return ApiResponse.<Void>builder()
                .message("password changed successfully")
                .build();
    }

    @PostMapping("/address")
    public ApiResponse<AddressUserResponse> addAddress(
            @Valid @RequestBody AddressUserRequest request
    ) {
        return ApiResponse.<AddressUserResponse>builder()
                .data(userService.addAddress(request))
                .message("address added successfully")
                .build();
    }

    @GetMapping("/address")
    public ApiResponse<List<AddressUserResponse>> getAllAddresses() {
        return ApiResponse.<List<AddressUserResponse>>builder()
                .data(userService.getAllAddresses())
                .message("get all addresses successfully")
                .build();
    }

    @PutMapping("/address/{id}")
    public ApiResponse<AddressUserResponse> updateAddress(
            @PathVariable Long id,
            @Valid @RequestBody AddressUserRequest request
    ) {
        return ApiResponse.<AddressUserResponse>builder()
                .data(userService.updateAddress(id, request))
                .message("address updated successfully")
                .build();
    }

    @DeleteMapping("/address/{id}")
    public ApiResponse<Void> deleteAddress(
            @PathVariable Long id
    ) {
        userService.deleteAddress(id);
        return ApiResponse.<Void>builder()
                .message("address deleted successfully")
                .build();
    }

}
