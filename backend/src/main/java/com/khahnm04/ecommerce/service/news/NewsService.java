package com.khahnm04.ecommerce.service.news;

import com.khahnm04.ecommerce.dto.request.news.NewsRequest;
import com.khahnm04.ecommerce.dto.response.news.NewsResponse;
import com.khahnm04.ecommerce.dto.response.PageResponse;
import com.khahnm04.ecommerce.dto.response.product.ProductResponse;
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
