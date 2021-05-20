package com.chenkesi.authorization.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

import javax.annotation.Resource;

/**
 * 认证授权服务配置类
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private AuthenticationManager authenticationManager;

    /**
     * 配置客户端服务器与资源服务器的关系
     * 客户端服务器可以访问哪些资源服务器
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("take-away-order") //相当于客户端的用户名
                .secret(passwordEncoder.encode("123456")) //相当于客户端的密码
                .scopes("read","write") //访问资源服务器有哪些权限
                .accessTokenValiditySeconds(3600) //token的有效期
                .resourceIds("take-away-restaurant") //可以访问哪些资源服务器
                .authorizedGrantTypes("password"); //认证方式 有四种后面具体介绍
    }

    /**
     * 配置哪些用户可以访问资源服务器
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
    }

    /**
     * 配置什么样的请求格式可以进行token验证
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //配置权限表达式
        security.checkTokenAccess("isAuthenticated()");
    }
}

