package com.offcn.webui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

//原因是引入数据库的依赖，但yml里没有配置会出问题
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class )  //不启用JDBC
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
public class WebStartApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebStartApplication.class);
    }
}