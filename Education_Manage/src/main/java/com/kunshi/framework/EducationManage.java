package com.kunshi.framework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching(proxyTargetClass = true)
public class EducationManage { 
	
	public static void main(String[] args) {
		SpringApplication.run(EducationManage.class, args);
	}
}  