package com.learning.interceptor.config;

import com.learning.interceptor.interceptor.ProductIdMappingInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final ProductIdMappingInterceptor productIdMappingInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(productIdMappingInterceptor)
                .addPathPatterns("/api/v1/products/**");
    }
}
