package com.wardenfar.paymybuddy.repository;

import com.wardenfar.paymybuddy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * User Repository
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find user by email
     */
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    /**
     * Test if an user exists with an email
     */
    boolean existsByEmail(String email);

}
