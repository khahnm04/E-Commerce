package com.khahnm04.ecommerce.service.news;

import com.khahnm04.ecommerce.common.enums.NewsStatus;
import com.khahnm04.ecommerce.dto.request.news.NewsRequest;
import com.khahnm04.ecommerce.dto.response.PageResponse;
import com.khahnm04.ecommerce.dto.response.news.NewsResponse;
import com.khahnm04.ecommerce.entity.news.News;
import com.khahnm04.ecommerce.exception.AppException;
import com.khahnm04.ecommerce.exception.ErrorCode;
import com.khahnm04.ecommerce.mapper.NewsMapper;
import com.khahnm04.ecommerce.repository.NewsRepository;
import com.khahnm04.ecommerce.service.upload.CloudinaryService;
import com.khahnm04.ecommerce.common.util.SortUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final CloudinaryService cloudinaryService;

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

    private News getNewsById(Long id) {
        return newsRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NEWS_NOT_FOUND));
    }

}
