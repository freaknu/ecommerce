package com.ecommerce.uploadservice.controller;

import com.ecommerce.uploadservice.common.ApiResponseFormat;
import com.ecommerce.uploadservice.service.ImageUploadingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/api/document")
@RequiredArgsConstructor
@CrossOrigin(origins = {
        "http://localhost:5173",
        "https://ecommerce-web-puce-sigma.vercel.app",
        "http://34.58.229.119:5173"
})
public class ImageUploadController {

    private final ImageUploadingService uploadingService;

    @PostMapping("/upload")
    public CompletableFuture<ResponseEntity<ApiResponseFormat<String>>> upload(
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        if (file.isEmpty()) {
            return CompletableFuture.completedFuture(
                    ResponseEntity.badRequest().body(
                            new ApiResponseFormat<>(
                                    null,
                                    "File is empty",
                                    false,
                                    HttpStatus.BAD_REQUEST.value(),
                                    LocalDateTime.now()
                            )
                    )
            );
        }

        return uploadingService.uploadImage(file.getBytes())
                .thenApply(url ->
                        ResponseEntity.ok(
                                new ApiResponseFormat<>(
                                        url,
                                        "Image uploaded successfully",
                                        true,
                                        HttpStatus.OK.value(),
                                        LocalDateTime.now()
                                )
                        )
                )
                .exceptionally(ex ->
                        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                                new ApiResponseFormat<>(
                                        null,
                                        "Upload failed: " + ex.getMessage(),
                                        false,
                                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                        LocalDateTime.now()
                                )
                        )
                );
    }
}
