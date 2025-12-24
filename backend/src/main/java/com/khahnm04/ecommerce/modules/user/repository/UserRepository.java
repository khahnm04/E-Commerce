package com.khahnm04.ecommerce.modules.user.repository;

import com.khahnm04.ecommerce.modules.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE :identifier IN (u.email, u.phoneNumber)")
    Optional<User> findByIdentifier(@Param("identifier") String identifier);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    Page<User> findAllByDeletedAtIsNotNull(Pageable pageable);

    Page<User> findAllByDeletedAtIsNull(Pageable pageable);

}
