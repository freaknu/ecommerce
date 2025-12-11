package com.ecommerce.uploadservice.controller;

import com.ecommerce.uploadservice.service.ImageUploadingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;


@Slf4j
@RestController
@RequestMapping("/api/document")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173","https://ecommerce-web-puce-sigma.vercel.app","http://34.58.229.119:5173"})
public class ImageUploadController {
    private final ImageUploadingService uploadingService;
    @PostMapping("/upload")
    public CompletableFuture<ResponseEntity<String>> upload(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return CompletableFuture.completedFuture(
                    ResponseEntity.badRequest().body("File is empty"));
        }

        return uploadingService.uploadImage(file.getBytes())
                .thenApply(url -> ResponseEntity.ok(url))
                .exceptionally(ex -> ResponseEntity.status(500).body("Upload failed: " + ex.getMessage()));
    }
}
