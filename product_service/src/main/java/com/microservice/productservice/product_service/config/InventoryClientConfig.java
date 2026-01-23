package com.microservice.productservice.product_service.config;

import com.microservice.productservice.product_service.clients.interfaces.InventoryClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class InventoryClientConfig {

    @Bean(name = "loadBalancedBuilder")
    @LoadBalanced
    public RestClient.Builder loadBalancedBuilder() {
        return RestClient.builder();
    }

    @Bean(name = "externalBuilder")
    @Primary
    public RestClient.Builder externalBuilder() {
        return RestClient.builder();
    }

    @Bean
    public InventoryClient inventoryClient(@Qualifier("loadBalancedBuilder") RestClient.Builder builder) {

        RestClient restClient = builder
                .baseUrl("http://inventory-service")
                .requestFactory(factoryWithTimeout())
                .build();

        HttpServiceProxyFactory proxy =
                HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();

        return proxy.createClient(InventoryClient.class);
    }

    private ClientHttpRequestFactory factoryWithTimeout() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(30000);
        factory.setReadTimeout(30000);
        return factory;
    }
}
