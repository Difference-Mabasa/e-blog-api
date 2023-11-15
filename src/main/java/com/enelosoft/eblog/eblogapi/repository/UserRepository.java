package com.enelosoft.eblog.eblogapi.repository;

import com.enelosoft.eblog.eblogapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String email);
    Optional<User> findByUsernameOrEmail(String username, String email);

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);


    //JPQL Query
    @Query("SELECT user FROM User WHERE" +
            "user.name LIKE CONCAT('%',:query, '%')" +
            "OR user.username LIKE CONCAT('%',:query, '%')")

    //Native Query
//    @Query(value = "SELECT * FROM users WHERE" +
//            "user.name LIKE CONCAT('%',:query, '%')" +
//            "OR user.username LIKE CONCAT('%',:query, '%')", nativeQuery = true)
    List<User> searchUsers(String query);
}
