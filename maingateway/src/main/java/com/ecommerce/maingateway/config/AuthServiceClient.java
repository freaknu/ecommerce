package com.ecommerce.maingateway.config;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;

public interface AuthServiceClient {
    @GetExchange("/api/detail/getCurrentUser")
    Object getCurrentUser(@RequestHeader("Authorization") String token);
}
