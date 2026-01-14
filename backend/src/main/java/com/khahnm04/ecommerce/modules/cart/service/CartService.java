package com.khahnm04.ecommerce.modules.cart.service;

import com.khahnm04.ecommerce.modules.cart.dto.request.CartItemRequest;
import com.khahnm04.ecommerce.modules.cart.dto.response.CartResponse;

public interface CartService {

    // Lấy giỏ hàng của user đang đăng nhập
    CartResponse getMyCart();

    // Thêm sản phẩm vào giỏ
    void addToCart(CartItemRequest request);

    // Cập nhật số lượng item (ví dụ: tăng từ 1 lên 2)
    void updateCartItem(Long itemId, Integer quantity);

    // Xóa 1 món khỏi giỏ
    void removeCartItem(Long itemId);

    // Xóa sạch giỏ hàng
    void clearCart();

}
