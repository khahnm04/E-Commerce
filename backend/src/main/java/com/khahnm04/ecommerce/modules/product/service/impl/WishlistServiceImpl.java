package com.khahnm04.ecommerce.modules.product.service.impl;

import com.khahnm04.ecommerce.shared.util.SortUtils;
import com.khahnm04.ecommerce.shared.dto.PageResponse;
import com.khahnm04.ecommerce.modules.product.dto.response.ProductResponse;
import com.khahnm04.ecommerce.modules.product.entity.Product;
import com.khahnm04.ecommerce.modules.user.entity.User;
import com.khahnm04.ecommerce.modules.product.entity.Wishlist;
import com.khahnm04.ecommerce.core.exception.AppException;
import com.khahnm04.ecommerce.core.exception.ErrorCode;
import com.khahnm04.ecommerce.modules.product.mapper.ProductMapper;
import com.khahnm04.ecommerce.modules.product.repository.ProductRepository;
import com.khahnm04.ecommerce.modules.product.repository.WishlistRepository;
import com.khahnm04.ecommerce.modules.product.service.WishlistService;
import com.khahnm04.ecommerce.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final UserService userService;

    @Override
    @Transactional
    public void addToWishlist(Long productId) {
        User user = userService.getCurrentUser();

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        if (wishlistRepository.existsByUserIdAndProductId(user.getId(), productId)) {
            throw new AppException(ErrorCode.WISHLIST_ALREADY_EXISTED);
        }

        Wishlist wishlist = Wishlist.builder()
                .user(user)
                .product(product)
                .build();

        wishlistRepository.save(wishlist);
        log.info("User {} added product {} to wishlist", user.getId(), productId);
    }

    @Override
    public PageResponse<ProductResponse> getMyWishlist(int page, int size, String sort) {
        User user = userService.getCurrentUser();
        Pageable pageable = PageRequest.of(page, size, SortUtils.parseSort(sort));

        Page<Wishlist> wishlistPage = wishlistRepository.findAllByUserId(user.getId(), pageable);

        return PageResponse.fromPage(wishlistPage.map(wishlist ->
                productMapper.fromProductToProductResponse(wishlist.getProduct())
        ));
    }

    @Override
    @Transactional
    public void removeFromWishlist(Long productId) {
        User user = userService.getCurrentUser();

        Wishlist wishlist = wishlistRepository.findByUserIdAndProductId(user.getId(), productId)
                .orElseThrow(() -> new AppException(ErrorCode.WISHLIST_NOT_FOUND));

        wishlistRepository.delete(wishlist);
        log.info("User {} removed product {} from wishlist", user.getId(), productId);
    }

}
