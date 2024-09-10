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

import com.salvi.dev.cps.fileupload.StorageProperties;
import com.salvi.dev.cps.fileupload.StorageService;
import com.salvi.dev.cps.service.ChannelProcessor;
import com.salvi.dev.cps.service.ChannelProcessorAImpl;
import com.salvi.dev.cps.service.ChannelProcessorBImpl;
import com.salvi.dev.cps.service.ChannelProcessorCImpl;
import com.salvi.dev.cps.service.ChannelProcessorYImpl;
import com.salvi.dev.cps.service.Input;
import com.salvi.dev.cps.service.Output;
import com.salvi.dev.cps.service.Parameter;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class ChannelProcessingApplication {

	public static void main(String[] args) {

		Input input = new Input();
		Parameter parameter = new Parameter();
		Output outputF1, outputF2, outputF3, outputF4 = new Output();

		ChannelProcessor processor;

		try {
			// Read input channels from channels.txt
			List<String> channelLines = Files.readAllLines(Paths.get("channels.txt"));
			input.getChannel().setX(readChannel("X", channelLines));

			// Read parameters from parameters.txt
			List<String> paramLines = Files.readAllLines(Paths.get("parameters.txt"));
			parameter = readParameters(paramLines);

			// Process channels using the defined functions
			processor = new ChannelProcessorYImpl();
			outputF1 = processor.function(parameter, input);
			input.getChannel().setY(outputF1.getChannel().getY());

			processor = new ChannelProcessorAImpl();
			outputF2 = processor.function(parameter, input);
			input.getChannel().setA(outputF2.getChannel().getA());

			processor = new ChannelProcessorBImpl();
			outputF3 = processor.function(parameter, input);
			input.getChannel().setB(outputF3.getChannel().getB());
			input.setMetric(outputF3.getMetric());

			processor = new ChannelProcessorCImpl();
			outputF4 = processor.function(parameter, input);// unused function4 output

			System.out.println("Metric b: " + outputF3.getMetric().getB());

			SpringApplication.run(ChannelProcessingApplication.class, args);
			System.out.println("Application running successfully");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}
}
