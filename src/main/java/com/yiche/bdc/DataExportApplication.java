package com.yiche.bdc;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;


@EnableEurekaClient
@SpringBootApplication
@EnableFeignClients
public class DataExportApplication {
    /**
     * Description: 应用启动函数定义
     *
     * @param args
     * @see
     */
    public static void main(String[] args) {
        SpringApplication.run(DataExportApplication.class, args);
    }
}
