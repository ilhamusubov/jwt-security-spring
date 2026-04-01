package com.example.jwtsecurity.repository;

import com.example.jwtsecurity.entity.RefreshTokenEntity;
import com.example.jwtsecurity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByToken(String token);

    Optional<RefreshTokenEntity> findByUser(UserEntity user);

    void delete(RefreshTokenEntity token);
}
