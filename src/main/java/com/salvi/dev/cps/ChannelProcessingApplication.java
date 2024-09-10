package com.salvi.dev.cps;

// import static com.salvi.dev.cps.service.ChannelProcessor.writeChannel;//implemented but unused functionality
import static com.salvi.dev.cps.service.ChannelProcessor.readChannel;
import static com.salvi.dev.cps.service.ChannelProcessor.readParameters;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.salvi.dev.cps.service.ChannelProcessor;
import com.salvi.dev.cps.service.ChannelProcessorAImpl;
import com.salvi.dev.cps.service.ChannelProcessorBImpl;
import com.salvi.dev.cps.service.ChannelProcessorCImpl;
import com.salvi.dev.cps.service.ChannelProcessorYImpl;
import com.salvi.dev.cps.service.Input;
import com.salvi.dev.cps.service.Output;
import com.salvi.dev.cps.service.Parameter;

@SpringBootApplication
@EnableConfigurationProperties
public class ChannelProcessingApplication {

	public static void main(String[] args) {
			SpringApplication.run(ChannelProcessingApplication.class, args);
			System.out.println("Application running successfully");
	}
}
