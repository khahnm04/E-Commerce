package com.khahnm04.ecommerce.modules.banner.repository;

import com.khahnm04.ecommerce.shared.enums.BannerPosition;
import com.khahnm04.ecommerce.shared.enums.BannerStatus;
import com.khahnm04.ecommerce.modules.banner.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {

    // Lấy danh sách request theo vị trí, chỉ lấy cái ACTIVE và sắp xếp
    List<Banner> findByPositionAndStatusOrderByDisplayOrderAsc(BannerPosition position, BannerStatus status);

}
