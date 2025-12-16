package com.khahnm04.ecommerce.mapper;

import com.khahnm04.ecommerce.dto.request.news.NewsRequest;
import com.khahnm04.ecommerce.dto.response.news.NewsResponse;
import com.khahnm04.ecommerce.entity.news.News;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface NewsMapper {

    @Mapping(target = "image", ignore = true)
    News fromNewsRequestToNews(NewsRequest request);

    @Mapping(target = "image", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateNews(@MappingTarget News news, NewsRequest request);

    NewsResponse toNewsResponse(News news);

}
