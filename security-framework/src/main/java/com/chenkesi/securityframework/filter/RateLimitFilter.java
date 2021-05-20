package com.chenkesi.securityframework.filter;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 流控过滤器
 * @see OncePerRequestFilter Spring原生接口，保证一个请求内，此过滤器只被执行一次
 */
@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private RateLimiter rateLimiter = RateLimiter.create(1);

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (rateLimiter.tryAcquire()){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }else {
            httpServletResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            PrintWriter writer = httpServletResponse.getWriter();
            writer.write("too many request");
            writer.flush();
            return;
        }
    }
}
