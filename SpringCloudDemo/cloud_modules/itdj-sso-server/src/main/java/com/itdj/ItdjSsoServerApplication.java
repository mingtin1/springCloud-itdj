package com.itdj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.itdj.mapper")
public class ItdjSsoServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItdjSsoServerApplication.class, args);
	}
}
