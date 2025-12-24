package com.khahnm04.ecommerce.modules.auth.repository;

import com.khahnm04.ecommerce.modules.auth.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
}
