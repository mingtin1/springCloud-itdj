package com.itdj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.itdj.admin"})
public class ItdjUpmsServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItdjUpmsServerApplication.class, args);
    }
}
