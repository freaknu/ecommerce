package com.notification_service.notification_service;

import com.notification_service.notification_service.config.FirebaseConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@SpringBootApplication()
@Import(FirebaseConfig.class)
public class NotificationServiceApplication {

	public static void main(String[] args) throws IOException {
        InputStream is = new ClassPathResource("firebase/firebase-service-account.json").getInputStream();
        System.out.println("File found and readable! Size: " + is.available());
        is.close();
        SpringApplication.run(NotificationServiceApplication.class, args);
	}

}
