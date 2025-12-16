package com.khahnm04.ecommerce.controller.admin;

import com.khahnm04.ecommerce.common.enums.UserStatus;
import com.khahnm04.ecommerce.dto.request.user.UserRequest;
import com.khahnm04.ecommerce.dto.response.ApiResponse;
import com.khahnm04.ecommerce.dto.response.PageResponse;
import com.khahnm04.ecommerce.dto.response.user.AddressUserResponse;
import com.khahnm04.ecommerce.dto.response.user.UserResponse;
import com.khahnm04.ecommerce.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("${api.prefix}/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserResponse> createUser(
            @Valid @RequestBody UserRequest request
    ) {
        return ApiResponse.<UserResponse>builder()
                .data(userService.createUser(request))
                .message("user created successfully")
                .build();
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getAllUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort
    ) {
        PageResponse<UserResponse> pageResponse = userService.getAllUsers(page - 1, size, sort);
        return ApiResponse.<List<UserResponse>>builder()
                .meta(pageResponse.getMeta())
                .data(pageResponse.getData())
                .message("get all users successfully")
                .build();
    }

    @GetMapping("/deleted")
    public ApiResponse<List<UserResponse>> getAllDeletedUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort
    ) {
        PageResponse<UserResponse> pageResponse = userService.getAllDeletedUsers(page - 1, size, sort);
        return ApiResponse.<List<UserResponse>>builder()
                .meta(pageResponse.getMeta())
                .data(pageResponse.getData())
                .message("get all users deleted successfully")
                .build();
    }

    @GetMapping("/{id}/addresses")
    public ApiResponse<List<AddressUserResponse>> getAllAddressesByUserId(
            @PathVariable Long id
    ) {
        return ApiResponse.<List<AddressUserResponse>>builder()
                .data(userService.getAllAddressesByUserId(id))
                .message("get all users successfully")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserById(
            @PathVariable Long id
    ) {
        return ApiResponse.<UserResponse>builder()
                .data(userService.getUserDetailById(id))
                .message("get user by id successfully")
                .build();
    }

    @PutMapping(value = "/{id}")
    public ApiResponse<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequest request

    ) {
        return ApiResponse.<UserResponse>builder()
                .data(userService.updateUser(id, request))
                .message("user updated successfully")
                .build();
    }

    @PatchMapping("/{id}/status")
    public ApiResponse<Void> updateUserStatus(
            @PathVariable Long id,
            @RequestParam UserStatus status
    ) {
        userService.updateUserStatus(id, status);
        return ApiResponse.<Void>builder()
                .message("user status updated successfully")
                .build();
    }

    @DeleteMapping("/{id}/soft-delete")
    public ApiResponse<Void> softDeleteUser(
            @PathVariable Long id
    ) {
        userService.softDeleteUser(id);
        return ApiResponse.<Void>builder()
                .message("user soft deleted successfully")
                .build();
    }

    @PatchMapping("/{id}/restore")
    public ApiResponse<Void> restoreUser(
            @PathVariable Long id
    ) {
        userService.restoreUser(id);
        return ApiResponse.<Void>builder()
                .message("user restored successfully")
                .build();
    }

}
