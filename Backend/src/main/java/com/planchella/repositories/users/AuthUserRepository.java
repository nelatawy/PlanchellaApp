package com.planchella.repositories.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.planchella.entities.AuthUserEntity;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUserEntity, Long> {
    AuthUserEntity findByUsername(String username);
    AuthUserEntity findByProviderId(String providerId);

}
