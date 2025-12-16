package com.khahnm04.ecommerce.service.product;

import com.khahnm04.ecommerce.dto.response.PageResponse;
import com.khahnm04.ecommerce.dto.response.product.ProductResponse;

public interface WishlistService {

    void addToWishlist(Long productId);
    void removeFromWishlist(Long productId);
    PageResponse<ProductResponse> getMyWishlist(int page, int size, String sort);

}
