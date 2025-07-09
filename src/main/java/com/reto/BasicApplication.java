package com.reto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class BasicApplication {
	public static void main(String[] args) {
		SpringApplication.run(BasicApplication.class, args);
	}
}
