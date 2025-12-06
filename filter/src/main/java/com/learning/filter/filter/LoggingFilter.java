package com.learning.filter.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest=(HttpServletRequest) servletRequest;
        String requestURI = httpServletRequest.getRequestURI();
        System.out.println("request uri is: "+requestURI);
        filterChain.doFilter(httpServletRequest,servletResponse);
    }
}
