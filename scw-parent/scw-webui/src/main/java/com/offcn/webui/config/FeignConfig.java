package com.offcn.webui.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FeignConfig {
    //获取日志的
    @Bean
    public Logger.Level getFeignlogger(){
        return Logger.Level.FULL;
    }
}
