package com.khahnm04.ecommerce.modules.cart.mapper;

import com.khahnm04.ecommerce.modules.cart.dto.response.CartItemResponse;
import com.khahnm04.ecommerce.modules.cart.dto.response.CartResponse;
import com.khahnm04.ecommerce.modules.cart.entity.Cart;
import com.khahnm04.ecommerce.modules.cart.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {

    // --- 1. Map CartItem ---
    @Mapping(source = "productVariant.id", target = "productVariantId")
    @Mapping(source = "productVariant.product.name", target = "productName")
    @Mapping(source = "productVariant.sku", target = "sku")
    @Mapping(source = "productVariant.image", target = "image")
    // Ép kiểu từ Double sang BigDecimal cho field price
    @Mapping(target = "price", expression = "java(java.math.BigDecimal.valueOf(item.getProductVariant().getPrice()))")
    // Tính thành tiền
    @Mapping(target = "subTotal", expression = "java(calculateSubTotal(item))")
    @Mapping(target = "maxStock", ignore = true)
    CartItemResponse toCartItemResponse(CartItem item);

    List<CartItemResponse> toCartItemResponseList(List<CartItem> items);

    // --- 2. Map Cart ---
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "cartItems", target = "items")
    @Mapping(target = "totalPrice", expression = "java(calculateTotalPrice(cart.getCartItems()))")
    @Mapping(target = "totalItems", expression = "java(calculateTotalItems(cart.getCartItems()))")
    CartResponse toCartResponse(Cart cart);

    // --- HELPER METHODS (Xử lý logic tính toán) ---

    // Tính thành tiền từng món (Giá x Số lượng)
    default BigDecimal calculateSubTotal(CartItem item) {
        if (item.getProductVariant() == null || item.getProductVariant().getPrice() == null) {
            return BigDecimal.ZERO;
        }
        // 1. Convert giá từ Double sang BigDecimal
        BigDecimal price = BigDecimal.valueOf(item.getProductVariant().getPrice());

        // 2. Convert số lượng từ Integer sang BigDecimal
        BigDecimal quantity = BigDecimal.valueOf(item.getQuantity());

        // 3. Nhân
        return price.multiply(quantity);
    }

    // Tính tổng tiền giỏ hàng (Cộng dồn subTotal)
    default BigDecimal calculateTotalPrice(List<CartItem> items) {
        if (items == null || items.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return items.stream()
                .map(this::calculateSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Đếm tổng số lượng item
    default Integer calculateTotalItems(List<CartItem> items) {
        if (items == null || items.isEmpty()) {
            return 0;
        }
        return items.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

}
