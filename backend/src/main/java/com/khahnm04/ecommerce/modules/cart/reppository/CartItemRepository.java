package com.khahnm04.ecommerce.modules.cart.reppository;

import com.khahnm04.ecommerce.modules.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // Tìm xem trong giỏ hàng này đã có sản phẩm này chưa?
    // (Để nếu có rồi thì cộng dồn số lượng, chưa có thì tạo mới)
    @Query("select c from CartItem c where c.cart.id = :cartId and c.productVariant.id = :productVariantId")
    Optional<CartItem> findByCartIdAndProductVariantId(Long cartId, Long productVariantId);

    // Xóa sạch các item trong 1 giỏ hàng (Dùng khi Clear Cart)
    @Modifying
    @Query("DELETE FROM CartItem ci WHERE ci.cart.id = :cartId")
    void deleteAllByCartId(@Param("cartId") Long cartId);

    // Đếm tổng số lượng sản phẩm trong giỏ (Dùng để hiển thị badge trên icon Cart)
    // Ví dụ: Mua 2 cái áo + 3 cái quần -> Trả về 5
    @Query("SELECT COALESCE(SUM(ci.quantity), 0) FROM CartItem ci WHERE ci.cart.id = :cartId")
    Integer countTotalItems(@Param("cartId") Long cartId);

}
