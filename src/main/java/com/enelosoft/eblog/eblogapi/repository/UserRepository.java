package com.enelosoft.eblog.eblogapi.repository;

import com.enelosoft.eblog.eblogapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String email);
    Optional<User> findByUsernameOrEmail(String username, String email);

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}