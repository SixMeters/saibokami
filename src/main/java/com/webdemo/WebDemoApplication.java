package com.webdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Spring Boot 应用程序主类
 * 
 * @SpringBootApplication 注解是一个组合注解，包含：
 * - @Configuration：标识为配置类
 * - @EnableAutoConfiguration：启用 Spring Boot 自动配置
 * - @ComponentScan：自动扫描组件（Controller、Service 等）
 */
@SpringBootApplication
@EnableScheduling
public class WebDemoApplication {
    /**
     * 应用程序入口方法
     * 
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 启动 Spring Boot 应用程序
        // 参数1：应用程序主类，用于读取配置和扫描组件
        // 参数2：命令行参数，可以传递配置参数
        SpringApplication.run(WebDemoApplication.class, args);
    }
}