package com.trade.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TradebotApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradebotApplication.class, args);
	}
}
