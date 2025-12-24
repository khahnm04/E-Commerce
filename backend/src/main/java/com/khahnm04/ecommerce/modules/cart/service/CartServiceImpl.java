package com.khahnm04.ecommerce.modules.cart.service;

import com.khahnm04.ecommerce.modules.cart.dto.request.CartItemRequest;
import com.khahnm04.ecommerce.modules.cart.dto.response.CartItemResponse;
import com.khahnm04.ecommerce.modules.cart.dto.response.CartResponse;
import com.khahnm04.ecommerce.modules.cart.entity.Cart;
import com.khahnm04.ecommerce.modules.cart.entity.CartItem;
import com.khahnm04.ecommerce.modules.product.entity.ProductVariant;
import com.khahnm04.ecommerce.modules.user.entity.User;
import com.khahnm04.ecommerce.core.exception.AppException;
import com.khahnm04.ecommerce.core.exception.ErrorCode;
import com.khahnm04.ecommerce.modules.cart.mapper.CartMapper;
import com.khahnm04.ecommerce.modules.cart.reppository.CartItemRepository;
import com.khahnm04.ecommerce.modules.cart.reppository.CartRepository;
import com.khahnm04.ecommerce.modules.product.repository.ProductVariantRepository;
import com.khahnm04.ecommerce.modules.inventory.service.InventoryService;
import com.khahnm04.ecommerce.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductVariantRepository variantRepository;
    private final InventoryService inventoryService;
    private final CartMapper cartMapper;
    private final UserService userService;

    @Override
    public CartResponse getMyCart() {
        User user = userService.getCurrentUser();
        Cart cart = getOrCreateCart(user);

        CartResponse response = cartMapper.toCartResponse(cart);

        // Điền thông tin Max Stock thực tế cho từng item để Frontend biết đường giới hạn
        for (CartItemResponse item : response.getItems()) {
            item.setMaxStock(inventoryService.getTotalStock(item.getProductVariantId()));
        }

        return response;
    }

    @Override
    @Transactional
    public void addToCart(CartItemRequest request) {
        User user = userService.getCurrentUser();
        Cart cart = getOrCreateCart(user);

        ProductVariant variant = variantRepository.findById(request.getProductVariantId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_VARIANT_NOT_FOUND));

        // 1. Kiểm tra tồn kho
        Long totalStock = inventoryService.getTotalStock(variant.getId());
        if (request.getQuantity() > totalStock) {
            throw new AppException(ErrorCode.OUT_OF_STOCK);
        }

        // 2. Kiểm tra xem item đã có trong giỏ chưa
        Optional<CartItem> existingItemOpt = cartItemRepository.findByCartIdAndProductVariantId(cart.getId(), variant.getId());

        if (existingItemOpt.isPresent()) {
            // Nếu có rồi -> Cộng dồn số lượng
            CartItem existingItem = existingItemOpt.get();
            int newQuantity = existingItem.getQuantity() + request.getQuantity();

            // Check tồn kho lần nữa cho tổng số lượng mới
            if (newQuantity > totalStock) {
                throw new AppException(ErrorCode.OUT_OF_STOCK);
            }
            existingItem.setQuantity(newQuantity);
            cartItemRepository.save(existingItem);
        } else {
            // Nếu chưa có -> Tạo mới
            CartItem newItem = CartItem.builder()
                    .cart(cart)
                    .productVariant(variant)
                    .quantity(request.getQuantity())
                    .build();
            cartItemRepository.save(newItem);
        }
    }

    @Override
    @Transactional
    public void updateCartItem(Long itemId, Integer quantity) {
        if (quantity <= 0) {
            throw new AppException(ErrorCode.INVALID_QUANTITY);
        }

        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));

        User currentUser = userService.getCurrentUser();
        if (!item.getCart().getUser().getId().equals(currentUser.getId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        Long totalStock = inventoryService.getTotalStock(item.getProductVariant().getId());
        if (quantity > totalStock) {
            throw new AppException(ErrorCode.OUT_OF_STOCK);
        }

        item.setQuantity(quantity);
        cartItemRepository.save(item);
    }

    @Override
    @Transactional
    public void removeCartItem(Long itemId) {
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));

        User currentUser = userService.getCurrentUser();
        if (!item.getCart().getUser().getId().equals(currentUser.getId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        cartItemRepository.delete(item);
    }

    @Override
    @Transactional
    public void clearCart() {
        User user = userService.getCurrentUser();
        Cart cart = getOrCreateCart(user);
        cartItemRepository.deleteAllByCartId(cart.getId());
    }

    private Cart getOrCreateCart(User user) {
        return cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    Cart newCart = Cart.builder().user(user).build();
                    return cartRepository.save(newCart);
                });
    }

}
