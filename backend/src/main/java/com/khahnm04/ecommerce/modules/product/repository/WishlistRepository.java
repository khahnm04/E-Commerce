package com.khahnm04.ecommerce.modules.product.repository;

import com.khahnm04.ecommerce.modules.product.entity.Wishlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    @Query("select (count(w.id) > 0) from Wishlist w where w.user.id = ?1 and w.product.id = ?2")
    boolean existsByUserIdAndProductId(Long userId, Long productId);

    @Query("select w from Wishlist w where w.user.id = ?1 and w.product.id = ?2")
    Optional<Wishlist> findByUserIdAndProductId(Long userId, Long productId);

    @Query("select w from Wishlist w where w.user.id = ?1")
    Page<Wishlist> findAllByUserId(Long userId, Pageable pageable);

    @Query("select count(w.id) from Wishlist w where w.user.id = ?1")
    Long countByUserId(Long userId);

}
