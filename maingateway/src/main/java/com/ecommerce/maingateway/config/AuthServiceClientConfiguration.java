package com.ecommerce.maingateway.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class AuthServiceClientConfiguration {
    @Bean
    @LoadBalanced
    public RestClient.Builder builder() {
        return RestClient.builder();
    }

    @Bean
    public AuthServiceClient authClient(RestClient.Builder builder) {
        RestClient client = builder
                .baseUrl("http://auth-service")
                .build();

        var adaptor = RestClientAdapter.create(client);
        var proxyFactor = HttpServiceProxyFactory.builderFor(adaptor).build();
        return proxyFactor.createClient(AuthServiceClient.class);

    }
}
