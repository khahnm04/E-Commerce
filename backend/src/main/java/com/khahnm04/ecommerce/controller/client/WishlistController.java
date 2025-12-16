package com.khahnm04.ecommerce.controller.client;

import com.khahnm04.ecommerce.dto.response.ApiResponse;
import com.khahnm04.ecommerce.dto.response.PageResponse;
import com.khahnm04.ecommerce.dto.response.product.ProductResponse;
import com.khahnm04.ecommerce.service.product.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/wishlists")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping("/{productId}")
    public ApiResponse<Void> addToWishlist(
            @PathVariable Long productId
    ) {
        wishlistService.addToWishlist(productId);
        return ApiResponse.<Void>builder()
                .message("Added to wishlist")
                .build();
    }

    @GetMapping
    public ApiResponse<List<ProductResponse>> getMyWishlist(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort
    ) {
        PageResponse<ProductResponse> pageResponse = wishlistService.getMyWishlist(page - 1, size, sort);
        return ApiResponse.<List<ProductResponse>>builder()
                .data(pageResponse.getData())
                .meta(pageResponse.getMeta())
                .message("Get my wishlist successfully")
                .build();
    }

    @DeleteMapping("/{productId}")
    public ApiResponse<Void> removeFromWishlist(
            @PathVariable Long productId
    ) {
        wishlistService.removeFromWishlist(productId);
        return ApiResponse.<Void>builder()
                .message("Removed from wishlist")
                .build();
    }

}
