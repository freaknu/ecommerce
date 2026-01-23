package com.notification_service.notification_service.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    private static final Logger logger = LoggerFactory.getLogger(FirebaseConfig.class);

    @Bean
    public FirebaseApp firebaseApp() throws Exception {
        logger.info("Initializing FirebaseApp from service account key...");

        String filePath = "firebase/firebase-service-account.json";
        ClassPathResource resource = new ClassPathResource(filePath);

        if (!resource.exists()) {
            logger.error("FIREBASE SERVICE ACCOUNT FILE NOT FOUND: {}", filePath);
            throw new IllegalStateException("Missing Firebase credentials: " + filePath);
        }

        logger.info("Found service account file: {}", filePath);

        try (InputStream serviceAccount = resource.getInputStream()) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp app = FirebaseApp.initializeApp(options);
                logger.info("SUCCESS: FirebaseApp [DEFAULT] initialized successfully!");
                return app;
            } else {
                logger.info("FirebaseApp already initialized, returning existing instance.");
                return FirebaseApp.getInstance();
            }
        }
    }
}