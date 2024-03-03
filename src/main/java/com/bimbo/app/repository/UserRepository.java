package com.bimbo.app.repository;

import com.bimbo.app.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    // Find user by email because is unique
    Optional<UserEntity> findByEmail(String email);
}
