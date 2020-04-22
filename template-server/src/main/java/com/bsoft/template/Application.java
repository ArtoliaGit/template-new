package com.bsoft.template;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动类
 * 开启swagger EnableSwagger2Doc
 * 开启异步 EnableAsync
 * 开启定时任务 EnableScheduling
 * 开启websocket EnableWebSocket
 */
@EnableSwagger2Doc
@EnableAsync
@EnableScheduling
//@EnableWebSocket
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
