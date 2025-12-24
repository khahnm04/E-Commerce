package com.khahnm04.ecommerce.modules.news.service;

import com.khahnm04.ecommerce.modules.news.dto.request.NewsRequest;
import com.khahnm04.ecommerce.modules.news.dto.response.NewsResponse;
import com.khahnm04.ecommerce.shared.dto.PageResponse;
import com.khahnm04.ecommerce.modules.product.dto.response.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NewsService {

    NewsResponse createNews(NewsRequest request);
    PageResponse<NewsResponse> getAllNews(int page, int size, String sort);
    PageResponse<NewsResponse> getAllDeletedNews(int page, int size, String sort);
    NewsResponse getNewsDetailById(Long id);
    NewsResponse getNewsDetailBySlug(String slug);
    NewsResponse updateNews(Long id, NewsRequest request, MultipartFile file);
    void updateNewsStatus(Long id, String status);
    void softDeleteNews(Long id);
    void deleteNews(Long id);
    void restoreNews(Long id);

    void addProductsToNews(Long newsId, List<Long> productIds);
    void removeProductFromNews(Long newsId, Long productId);
    List<ProductResponse> getProductsByNews(Long newsId);

}
