package com.khahnm04.ecommerce.repository;

import com.khahnm04.ecommerce.common.enums.BannerPosition;
import com.khahnm04.ecommerce.common.enums.BannerStatus;
import com.khahnm04.ecommerce.entity.content.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {

    // Lấy danh sách banner theo vị trí, chỉ lấy cái ACTIVE và sắp xếp
    List<Banner> findByPositionAndStatusOrderByDisplayOrderAsc(BannerPosition position, BannerStatus status);

}
