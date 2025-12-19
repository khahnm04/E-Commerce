package com.khahnm04.ecommerce.repository;

import com.khahnm04.ecommerce.entity.news.NewsProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NewsProductRepository extends JpaRepository<NewsProduct, Long> {

    // Tìm tất cả sản phẩm thuộc 1 bài news
    @Query("select n from NewsProduct n where n.news.id = ?1")
    List<NewsProduct> findAllByNewsId(Long newsId);

    // Xóa liên kết
    @Transactional
    @Modifying
    @Query("delete from NewsProduct n where n.news.id = ?1 and n.product.id = ?2")
    void deleteByNewsIdAndProductId(Long newsId, Long productId);

    // Check tồn tại
    @Query("select (count(n) > 0) from NewsProduct n where n.news.id = ?1 and n.product.id = ?2")
    boolean existsByNewsIdAndProductId(Long newsId, Long productId);

}
