package com.khahnm04.ecommerce.modules.user.repository;

import com.khahnm04.ecommerce.modules.user.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
