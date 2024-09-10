package com.salvi.dev.cps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class ChannelProcessingApplication {

	public static void main(String[] args) {
			SpringApplication.run(ChannelProcessingApplication.class, args);
			System.out.println("Application running successfully");
	}
}
