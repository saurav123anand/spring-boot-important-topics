package com.learning.interceptor.interceptor;

import com.learning.interceptor.dto.ProductResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
@Slf4j
public class ProductResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        // only intercept controller method that returns ProductResponseDto;
        return ProductResponseDto.class.isAssignableFrom(returnType.getParameterType());

    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (!(body instanceof ProductResponseDto dto)) return body;

        HttpServletRequest servletRequest =
                ((ServletServerHttpRequest) request).getServletRequest();

        Object originalExternalIdAttr =
                servletRequest.getAttribute(ProductIdMappingInterceptor.ATTR_ORIGINAL_EXTERNAL_ID);

        if (originalExternalIdAttr instanceof Long externalId) {
            dto.setOriginalExternalId(externalId);
            dto.setMetaMessage("Product fetched using external id mapping");
        } else {
            dto.setMetaMessage("Product fetched directly by UUID");
        }

        log.debug("Appended extra response fields for product {}", dto.getId());

        return dto;
    }
}
