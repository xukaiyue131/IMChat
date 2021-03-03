package com.xky.imchat.config;

import com.xky.imchat.interceptor.JwtInterceptor;
import com.xky.imchat.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class JwtConfig implements WebMvcConfigurer {

@Autowired
    RedisUtil redisUtil;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //配置拦截器
        registry.addInterceptor(new JwtInterceptor(redisUtil)).excludePathPatterns("/imchat/user/login","/imchat/user/refresh","/imchat/user/image","/imchat/user/register");
    }

    @Bean
    public JwtInterceptor getInterceptor(){
        return  new JwtInterceptor(redisUtil);
    }

}
