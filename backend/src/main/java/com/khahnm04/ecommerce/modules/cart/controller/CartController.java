package com.khahnm04.ecommerce.modules.cart.controller;

import com.khahnm04.ecommerce.modules.cart.dto.request.CartItemRequest;
import com.khahnm04.ecommerce.shared.dto.ApiResponse;
import com.khahnm04.ecommerce.modules.cart.dto.response.CartResponse;
import com.khahnm04.ecommerce.modules.cart.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // 1. Xem giỏ hàng hiện tại
    // GET /api/v1/cart
    @GetMapping
    public ApiResponse<CartResponse> getMyCart() {
        return ApiResponse.<CartResponse>builder()
                .data(cartService.getMyCart())
                .message("Lấy thông tin giỏ hàng thành công")
                .build();
    }

    // 2. Thêm sản phẩm vào giỏ
    @PostMapping
    public ApiResponse<Void> addToCart(@Valid @RequestBody CartItemRequest request) {
        cartService.addToCart(request);
        return ApiResponse.<Void>builder()
                .message("Đã thêm sản phẩm vào giỏ hàng")
                .build();
    }

    // 3. Cập nhật số lượng item
    // PUT /api/v1/cart/items/{itemId}?quantity=5
    @PutMapping("/items/{itemId}")
    public ApiResponse<Void> updateCartItem(
            @PathVariable Long itemId,
            @RequestParam Integer quantity
    ) {
        cartService.updateCartItem(itemId, quantity);
        return ApiResponse.<Void>builder()
                .message("Cập nhật số lượng thành công")
                .build();
    }

    // 4. Xóa 1 sản phẩm khỏi giỏ
    // DELETE /api/v1/cart/items/{itemId}
    @DeleteMapping("/items/{itemId}")
    public ApiResponse<Void> removeCartItem(@PathVariable Long itemId) {
        cartService.removeCartItem(itemId);
        return ApiResponse.<Void>builder()
                .message("Đã xóa sản phẩm khỏi giỏ hàng")
                .build();
    }

    // 5. Xóa sạch giỏ hàng
    // DELETE /api/v1/cart
    @DeleteMapping
    public ApiResponse<Void> clearCart() {
        cartService.clearCart();
        return ApiResponse.<Void>builder()
                .message("Đã xóa toàn bộ giỏ hàng")
                .build();
    }

}
