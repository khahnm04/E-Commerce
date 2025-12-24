package com.khahnm04.ecommerce.modules.news.controller;

import com.khahnm04.ecommerce.modules.news.dto.request.NewsRequest;
import com.khahnm04.ecommerce.shared.dto.ApiResponse;
import com.khahnm04.ecommerce.shared.dto.PageResponse;
import com.khahnm04.ecommerce.modules.news.dto.response.NewsResponse;
import com.khahnm04.ecommerce.modules.news.service.NewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("${api.prefix}/admin/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<NewsResponse> createNews(
            @Valid @ModelAttribute NewsRequest request
    ) {
        return ApiResponse.<NewsResponse>builder()
                .data(newsService.createNews(request))
                .message("news created successfully")
                .build();
    }

    @GetMapping
    public ApiResponse<List<NewsResponse>> getAllNews(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort
    ) {
        PageResponse<NewsResponse> pageResponse = newsService.getAllNews(page - 1, size, sort);
        return ApiResponse.<List<NewsResponse>>builder()
                .data(pageResponse.getData())
                .meta(pageResponse.getMeta())
                .message("get all news successfully")
                .build();
    }

    @GetMapping("/deleted")
    public ApiResponse<List<NewsResponse>> getAllDeletedNews(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort
    ) {
        PageResponse<NewsResponse> pageResponse = newsService.getAllDeletedNews(page - 1, size, sort);
        return ApiResponse.<List<NewsResponse>>builder()
                .data(pageResponse.getData())
                .meta(pageResponse.getMeta())
                .message("get all soft deleted news successfully")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<NewsResponse> getNewsDetailById(
            @PathVariable Long id
    ) {
        return ApiResponse.<NewsResponse>builder()
                .data(newsService.getNewsDetailById(id))
                .message("get news detail by id successfully")
                .build();
    }

    @GetMapping("/slug/{slug}")
    public ApiResponse<NewsResponse> getNewsDetailBySlug(
            @PathVariable String slug
    ) {
        return ApiResponse.<NewsResponse>builder()
                .data(newsService.getNewsDetailBySlug(slug))
                .message("get news detail by slug successfully")
                .build();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<NewsResponse> updateNews(
            @PathVariable Long id,
            @Valid NewsRequest request,
            @RequestPart(value = "image", required = false) MultipartFile file
    ) {
        return ApiResponse.<NewsResponse>builder()
                .data(newsService.updateNews(id, request, file))
                .message("updated news")
                .build();
    }

    @PatchMapping("/{id}/status")
    public ApiResponse<Void> updateNewsStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        newsService.updateNewsStatus(id, status);
        return ApiResponse.<Void>builder()
                .message("news status updated successfully")
                .build();
    }

    @DeleteMapping("/{id}/soft-delete")
    public ApiResponse<Void> softDeleteNews(
            @PathVariable Long id
    ) {
        newsService.softDeleteNews(id);
        return ApiResponse.<Void>builder()
                .message("soft deleted news")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteNews(
            @PathVariable Long id
    ) {
        newsService.deleteNews(id);
        return ApiResponse.<Void>builder()
                .message("deleted news")
                .build();
    }

    @PatchMapping("/{id}/restore")
    public ApiResponse<Void> restoreNews(@PathVariable Long id) {
        newsService.restoreNews(id);
        return ApiResponse.<Void>builder()
                .message("news restored successfully")
                .build();
    }

}
