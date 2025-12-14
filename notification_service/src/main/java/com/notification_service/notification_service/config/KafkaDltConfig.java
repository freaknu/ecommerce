package com.notification_service.notification_service.config;

import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaDltConfig {

    @Bean
    public ProducerFactory<String, byte[]> byteArrayProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put("bootstrap.servers", "localhost:29092");
        configProps.put("key.serializer", StringSerializer.class);
        configProps.put("value.serializer", ByteArraySerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, byte[]> byteArrayKafkaTemplate() {
        return new KafkaTemplate<>(byteArrayProducerFactory());
    }
}