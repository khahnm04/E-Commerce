package com.khahnm04.ecommerce.repository;

import com.khahnm04.ecommerce.entity.auth.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
}
