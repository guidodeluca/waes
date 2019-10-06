package com.example.waes.test.filter;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

@Component
@Log4j2
public class PerformanceFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LocalDateTime beginning = LocalDateTime.now();
        filterChain.doFilter(servletRequest, servletResponse);
        Duration timeElapse = Duration.between(beginning, LocalDateTime.now());
        log.info("Elapse time for {} = {} milliseconds", ((HttpServletRequest) servletRequest).getRequestURI(), Duration.ofSeconds(timeElapse.getSeconds(), timeElapse.getNano()).toMillis());
    }
}
