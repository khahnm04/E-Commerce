package com.khahnm04.ecommerce.modules.product.service;

import com.khahnm04.ecommerce.shared.dto.PageResponse;
import com.khahnm04.ecommerce.modules.product.dto.response.ProductResponse;

public interface WishlistService {

    void addToWishlist(Long productId);
    void removeFromWishlist(Long productId);
    PageResponse<ProductResponse> getMyWishlist(int page, int size, String sort);

}
