## Filter

Filter is a component that allows us to intercept HTTP requests before reaching to the DispatcherServlet. 
It is used to handle the cross-cutting concerns like logging, authentication, authorization, etc.

## Filter Lifecycle

1. init() - called once when the filter is initialized
2. doFilter() - called for every request
3. destroy() - called once when the filter is destroyed

## Filter Registration

1. @Component
2. @FilterRegistrationBean
3. web.xml

We can create the filter by implementing the Filter interface or by extending the OncePerRequestFilter class.

## Complete flow

Client -> Filter -> DispatcherServlet -> Controller

Filter is a part of Servlet API. It is not a part of Spring. 

# Dispatcher servlet is a part of Spring. It is a servlet that dispatches the request to the appropriate handler.
Dispatcher servlet resides between the Filter and the Controller.


## Filter Example

LoggingFilter.java
```java
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
```

filterChain.doFilter will call the next filter or the dispatcher servlet. it's a callback method that allows the filter to pass the request to the next filter or the dispatcher servlet.

