package com.kim.dibt.security.repo;

import com.kim.dibt.security.models.Role;
import com.kim.dibt.security.models.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
