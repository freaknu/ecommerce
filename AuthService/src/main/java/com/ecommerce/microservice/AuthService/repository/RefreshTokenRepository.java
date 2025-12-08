package com.ecommerce.microservice.AuthService.repository;

import com.ecommerce.microservice.AuthService.model.AppUser;
import com.ecommerce.microservice.AuthService.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    void deleteByAppUser(AppUser user);
}
