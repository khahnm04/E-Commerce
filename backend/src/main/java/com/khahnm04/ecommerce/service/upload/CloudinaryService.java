package com.khahnm04.ecommerce.service.upload;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public String upload(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        try {
            var uploadResult = cloudinary.uploader()
                    .upload(file.getBytes(), ObjectUtils.asMap(
                            "folder", "fashion-ecommerce",
                            "resource_type", "auto"
                    ));
            return uploadResult.get("secure_url").toString();
        } catch (Exception e) {
            log.error("Upload failed: {}", e.getMessage());
            throw new RuntimeException("Failed to upload file: " + e.getMessage());
        }
    }

    public List<String> uploadFiles(List<MultipartFile> files) {
        if (files == null || files.isEmpty()) return List.of();

        // Chạy song song tất cả các file cùng lúc
        List<CompletableFuture<String>> futures = files.stream()
                .map(file -> CompletableFuture.supplyAsync(() -> this.upload(file)))
                .toList();

        // Chờ tất cả xong và gom kết quả lại
        return futures.stream()
                .map(CompletableFuture::join)
                .filter(Objects::nonNull)
                .toList();
    }

}
