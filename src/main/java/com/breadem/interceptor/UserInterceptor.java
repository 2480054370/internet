package com.breadem.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class UserInterceptor extends WebMvcConfigurerAdapter {
	/**
     * 添加拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserHandlerInterceptor())
                //添加需要验证登录用户操作权限的请求
                .addPathPatterns("/party", "/profile", "/config", "/classPhoto")
                //排除不需要验证登录用户操作权限的请求
                .excludePathPatterns("");
    }
}
