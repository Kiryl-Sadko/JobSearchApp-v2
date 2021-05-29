package com.epam.esm.repository;

import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findUserByNameStartsWith(String name, Pageable pageable);

    Page<User> findUsersByRolesIn(Pageable pageable, Role... roles);

    User findTopByOrderByIdDesc();

    Optional<User> findByName(String name);
}
