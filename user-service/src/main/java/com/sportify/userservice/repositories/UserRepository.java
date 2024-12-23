package com.sportify.userservice.repositories;

import com.sportify.userservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findById(UUID id);
    User findByEmail(String email);
    User findByPhone(String phone);
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.password = :password WHERE u.username = :username")
    void updatePasswordByUsername(@Param("username") String username, @Param("password") String password);
}

