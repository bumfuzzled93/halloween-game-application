package com.mavrictan.halloweengameapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class HalloweenGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(HalloweenGameApplication.class, args);
	}

}
