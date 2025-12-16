package com.khahnm04.ecommerce.repository;

import com.khahnm04.ecommerce.entity.product.ProductQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductQuestionRepository extends JpaRepository<ProductQuestion, Long> {

    @Query("SELECT pq FROM ProductQuestion pq WHERE pq.product.id = :productId")
    List<ProductQuestion> findAllQuestionsByProductId(Long productId);

}
