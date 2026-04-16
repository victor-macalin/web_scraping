package com.dev.victor.web_scrapping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class 	 WebScrappingApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebScrappingApplication.class, args);

	}

}
