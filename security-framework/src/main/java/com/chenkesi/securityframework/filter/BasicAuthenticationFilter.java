package com.chenkesi.securityframework.filter;

import com.chenkesi.securityframework.domain.entity.User;
import com.chenkesi.securityframework.repository.UserRepository;
import com.lambdaworks.crypto.SCryptUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Base64Utils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * Http Basic认证
 */
public class BasicAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authorization = httpServletRequest.getHeader("Authorization");
        if (StringUtils.isNotBlank(authorization)){
            String token64 = StringUtils.substringAfter(authorization, "Basic ");
            String token = new java.lang.String(Base64Utils.decodeFromString(token64));
            String[] strings = StringUtils.splitByWholeSeparatorPreserveAllTokens(token, ":");
            User user = userRepository.findByUsername(strings[0]);
            if (Objects.isNull(user) && SCryptUtil.check(strings[1],user.getPassword())){
                httpServletRequest.setAttribute("user",user);
            }
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
