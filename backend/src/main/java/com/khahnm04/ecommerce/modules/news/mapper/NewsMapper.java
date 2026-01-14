package com.khahnm04.ecommerce.modules.news.mapper;

import com.khahnm04.ecommerce.modules.news.dto.request.NewsRequest;
import com.khahnm04.ecommerce.modules.news.dto.response.NewsResponse;
import com.khahnm04.ecommerce.modules.news.entity.News;
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
