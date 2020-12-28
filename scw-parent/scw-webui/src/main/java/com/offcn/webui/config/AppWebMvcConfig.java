package com.offcn.webui.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class AppWebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //如果controller仅仅用于转发页面，那在当前方法中配置映射即可
        //当访问login.html页面，相当于登录的请求
        registry.addViewController("/login.html").setViewName("login");
    }
}
