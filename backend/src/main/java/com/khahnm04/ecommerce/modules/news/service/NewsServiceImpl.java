package com.khahnm04.ecommerce.modules.news.service;

import com.khahnm04.ecommerce.shared.enums.NewsStatus;
import com.khahnm04.ecommerce.modules.news.dto.request.NewsRequest;
import com.khahnm04.ecommerce.shared.dto.PageResponse;
import com.khahnm04.ecommerce.modules.news.dto.response.NewsResponse;
import com.khahnm04.ecommerce.modules.product.dto.response.ProductResponse;
import com.khahnm04.ecommerce.modules.news.entity.News;
import com.khahnm04.ecommerce.modules.news.entity.NewsProduct;
import com.khahnm04.ecommerce.modules.product.entity.Product;
import com.khahnm04.ecommerce.core.exception.AppException;
import com.khahnm04.ecommerce.core.exception.ErrorCode;
import com.khahnm04.ecommerce.modules.news.mapper.NewsMapper;
import com.khahnm04.ecommerce.modules.product.mapper.ProductMapper;
import com.khahnm04.ecommerce.modules.news.repository.NewsProductRepository;
import com.khahnm04.ecommerce.modules.news.repository.NewsRepository;
import com.khahnm04.ecommerce.modules.product.repository.ProductRepository;
import com.khahnm04.ecommerce.modules.storage.service.CloudinaryService;
import com.khahnm04.ecommerce.shared.util.SortUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final CloudinaryService cloudinaryService;
    private final ProductRepository productRepository;
    private final NewsProductRepository newsProductRepository;
    private final ProductMapper productMapper;

    @Override
    public NewsResponse createNews(NewsRequest request) {
        if (newsRepository.existsBySlug(request.getSlug())) {
            throw new AppException(ErrorCode.NEWS_EXISTED);
        }

        News news = newsMapper.fromNewsRequestToNews(request);
        news.setImage(cloudinaryService.upload(request.getImage()));

        News savedNews = newsRepository.save(news);
        log.info("News created with id = {}", savedNews.getId());
        return newsMapper.toNewsResponse(savedNews);
    }

    @Override
    public PageResponse<NewsResponse> getAllNews(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, SortUtils.parseSort(sort));
        Page<News> newsPage = newsRepository.findAllByDeletedAtIsNull(pageable);
        Page<NewsResponse> dtoPage = newsPage.map(newsMapper::toNewsResponse);
        return PageResponse.fromPage(dtoPage);
    }

    @Override
    public PageResponse<NewsResponse> getAllDeletedNews(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, SortUtils.parseSort(sort));
        Page<News> newsPage = newsRepository.findAllByDeletedAtIsNotNull(pageable);
        Page<NewsResponse> dtoPage = newsPage.map(newsMapper::toNewsResponse);
        return PageResponse.fromPage(dtoPage);
    }

    @Override
    public NewsResponse getNewsDetailById(Long id) {
        return newsMapper.toNewsResponse(getNewsById(id));
    }

    @Override
    public NewsResponse getNewsDetailBySlug(String slug) {
        News news = newsRepository.findBySlug(slug)
                .orElseThrow(() -> new AppException(ErrorCode.NEWS_NOT_FOUND));
        return newsMapper.toNewsResponse(news);
    }

    @Override
    public NewsResponse updateNews(Long id, NewsRequest request, MultipartFile file) {
        News news = getNewsById(id);

        if (!StringUtils.hasText(request.getSlug())
                && newsRepository.existsBySlugIgnoreCaseAndIdNot(request.getSlug(), id)) {
            throw new AppException(ErrorCode.NEWS_EXISTED);
        }

        newsMapper.updateNews(news, request);
        news.setImage(cloudinaryService.upload(file));

        News savedNews = newsRepository.save(news);
        log.info("Updated news with id = {}", savedNews.getId());
        return newsMapper.toNewsResponse(savedNews);
    }

    @Override
    public void updateNewsStatus(Long id, String status) {
        News news = getNewsById(id);

        boolean isValid = Arrays.stream(NewsStatus.values())
                .anyMatch(e -> e.name().equalsIgnoreCase(status));

        if (!isValid) {
            throw new AppException(ErrorCode.INVALID_ENUM_VALUE);
        }

        news.setStatus(NewsStatus.valueOf(status));
        newsRepository.save(news);
        log.info("Updated status news with id = {}", news.getId());
    }

    @Override
    public void softDeleteNews(Long id) {
        News news = getNewsById(id);
        news.setDeletedAt(LocalDateTime.now());
        newsRepository.save(news);
        log.info("Soft deleted news with id {}", id);
    }

    @Override
    public void deleteNews(Long id) {
        News news = getNewsById(id);
        newsRepository.delete(news);
        log.info("Deleted news with id {}", id);
    }

    @Override
    public void restoreNews(Long id) {
        News news = getNewsById(id);
        news.setDeletedAt(null);
        newsRepository.save(news);
        log.info("News restored with id {}", id);
    }

    // Trong NewsServiceImpl.java

    @Override
    @Transactional
    public void addProductsToNews(Long newsId, List<Long> productIds) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new AppException(ErrorCode.NEWS_NOT_FOUND));

        for (Long productId : productIds) {
            // Check nếu chưa có thì mới thêm
            if (!newsProductRepository.existsByNewsIdAndProductId(newsId, productId)) {
                Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

                NewsProduct np = NewsProduct.builder()
                        .news(news)
                        .product(product)
                        // .createdBy(currentUser.getId()) // Nếu có user login
                        .build();
                newsProductRepository.save(np);
            }
        }
    }

    @Override
    @Transactional
    public void removeProductFromNews(Long newsId, Long productId) {
        newsProductRepository.deleteByNewsIdAndProductId(newsId, productId);
    }

    @Override
    public List<ProductResponse> getProductsByNews(Long newsId) {
        // Lấy list NewsProduct -> map sang Product -> map sang ProductResponse
        List<NewsProduct> list = newsProductRepository.findAllByNewsId(newsId);
        return list.stream()
                .map(np -> productMapper.fromProductToProductResponse(np.getProduct()))
                .toList();
    }

    private News getNewsById(Long id) {
        return newsRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NEWS_NOT_FOUND));
    }

}
