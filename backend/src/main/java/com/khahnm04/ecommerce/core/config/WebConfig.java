package com.khahnm04.ecommerce.core.config;

import com.khahnm04.ecommerce.shared.enums.CategoryStatus;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToCategoryStatusConverter());
    }

    // Class converter nội bộ
    private static class StringToCategoryStatusConverter implements Converter<String, CategoryStatus> {
        @Override
        public CategoryStatus convert(String source) {
            // Sử dụng hàm fromString bạn đã viết trong Enum
            return CategoryStatus.fromString(source);
        }
    }
}