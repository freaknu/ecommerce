package com.ecommerce.uploadservice.service;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageUploadingService {
    private final Cloudinary cloudinary;

    @Async
    public CompletableFuture<String> uploadImage(byte[] fileBytes) {
        if (fileBytes == null || fileBytes.length == 0) {
            log.error("Attempted to upload null or empty file bytes");
            throw new IllegalArgumentException("File bytes cannot be null or empty");
        }

        try {
            Map uploadResult = cloudinary.uploader().upload(fileBytes,
                    ObjectUtils.asMap(
                            "folder", "springboot_uploads"));
            String url = uploadResult.get("secure_url").toString();
            log.info("Cloudinary upload successful for URL: {}", url);
            return CompletableFuture.completedFuture(url);
        } catch (Exception e) {
            log.error("Failed   to upload to Cloudinary for ID {}: {}", e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }
}