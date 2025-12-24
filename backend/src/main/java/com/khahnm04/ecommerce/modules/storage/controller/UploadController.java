package com.khahnm04.ecommerce.modules.storage.controller;

import com.khahnm04.ecommerce.shared.dto.ApiResponse;
import com.khahnm04.ecommerce.modules.storage.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("${api.prefix}/upload")
@RequiredArgsConstructor
public class UploadController {

    private final CloudinaryService cloudinaryService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<List<String>> uploadFiles(
            @RequestPart("files") List<MultipartFile> files
    ) {
        List<String> urls = cloudinaryService.uploadFiles(files);
        return ApiResponse.<List<String>>builder()
                .message("Upload " + urls.size() + " files successfully")
                .data(urls)
                .build();
    }

}
