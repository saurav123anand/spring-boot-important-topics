package com.learning.interceptor.interceptor;

import com.learning.interceptor.entiity.ProductKeyMapping;
import com.learning.interceptor.exception.ResourceNotFoundException;
import com.learning.interceptor.repository.ProductKeyMappingRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductIdMappingInterceptor implements HandlerInterceptor {

    private final ProductKeyMappingRepository productKeyMappingRepository;

    public static final String ATTR_ORIGINAL_EXTERNAL_ID = "originalExternalId";

    @SuppressWarnings("unchecked")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("request reached to interceptor");
        // check if the request is not for controller
        if (!(handler instanceof HandlerMethod handlerMethod)) return true;

        String requestUri=request.getRequestURI();
        log.info("request URI is: {}", requestUri);
        // apply the interceptor for particular api path matcher(product controller)
        if (!requestUri.startsWith("/api/v1/products/")) return true;

        Object attribute = request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        log.info("attribute is: {}", attribute);

        if (!(attribute instanceof Map<?,?> keys)) return true;

        Map<String,String> originalVars=(Map<String, String>) attribute;
        Map<String, String> uriTemplateVars = new HashMap<>(originalVars);

        log.info("uri template vars are: {}", uriTemplateVars);

        // getting the path variable(id) from map of path variables
        String id = uriTemplateVars.get("id");

        log.info("id is: {}", id);
        if (id==null) return true;

        Long externalId;

        try{
            externalId=Long.parseLong(id);
        }
        catch (NumberFormatException ex){
            log.info("invalid external id ");
            throw new ResourceNotFoundException("Invalid product id");
        }

        log.info("external id is: {}", externalId);

        Optional<ProductKeyMapping> mappingOpt = productKeyMappingRepository.findByExternalId(externalId);
        ProductKeyMapping mapping = mappingOpt.orElseThrow(
                () -> new ResourceNotFoundException("No product mapping found for id " + externalId)
        );

        log.info("product key mapping is: {}", mapping);

        UUID productUuid = mapping.getProductId();

        log.info("product uuid is: {}", productUuid);
        if (productUuid == null) {
            throw new ResourceNotFoundException("Product UUID not mapped for id " + externalId);
        }

        // Store original external id for later (ResponseBodyAdvice)
        request.setAttribute(ATTR_ORIGINAL_EXTERNAL_ID,externalId);
        // Replace path variable with UUID string so controller sees UUID
        uriTemplateVars.put("id",productUuid.toString());
        request.setAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, uriTemplateVars);
        log.info("mapped external id {} to product uuid {}", externalId, productUuid);
        return true;
    }


}
