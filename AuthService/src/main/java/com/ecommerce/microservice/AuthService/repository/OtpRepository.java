package com.ecommerce.microservice.AuthService.repository;

import com.ecommerce.microservice.AuthService.model.Otps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository extends JpaRepository<Otps,Long> {
}
