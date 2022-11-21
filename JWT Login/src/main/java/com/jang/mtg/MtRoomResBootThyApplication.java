package com.jang.mtg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude=SecurityAutoConfiguration.class)
public class MtRoomResBootThyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MtRoomResBootThyApplication.class, args);
	}

}
