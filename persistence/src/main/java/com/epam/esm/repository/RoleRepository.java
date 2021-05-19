package com.epam.esm.repository;

import com.epam.esm.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findTopByOrderByIdDesc();

    Optional<Role> findByName(String name);
}
