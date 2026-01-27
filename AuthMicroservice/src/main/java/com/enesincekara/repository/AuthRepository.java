package com.enesincekara.repository;

import com.enesincekara.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;
import java.util.UUID;

public interface AuthRepository extends JpaRepository<Auth, Long> {

    Optional<Auth> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<Auth> findById(UUID id);
    Optional<Auth> findByUsernameAndIsActiveTrue(String username);

    Optional<Auth> findByIdAndIsActiveTrue(UUID id);
}
