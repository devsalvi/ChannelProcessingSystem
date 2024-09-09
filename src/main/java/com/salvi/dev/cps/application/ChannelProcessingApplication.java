package com.salvi.dev.cps.application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.salvi.dev.cps.service.ChannelProcessor;
import com.salvi.dev.cps.service.Input;
import com.salvi.dev.cps.service.Output;
import com.salvi.dev.cps.service.Parameter;

@SpringBootApplication
public class ChannelProcessingApplication {

	public static void main(String[] args) {
		ChannelProcessor processor = new ChannelProcessor();
		Input input = new Input();
		Parameter parameter = new Parameter();
		Output outputs = new Output();

		try {
			// Read input channels from channels.txt
			List<String> channelLines = Files.readAllLines(Paths.get("channels.txt"));
			input.getChannel().setX(processor.readChannel("X", channelLines));

			// Read parameters from parameters.txt
			List<String> paramLines = Files.readAllLines(Paths.get("parameters.txt"));
			parameter = processor.readParameters(paramLines);

			// Process channels using the defined functions
			outputs = processor.getChannelDataForY(parameter, input);
			input.getChannel().setY(outputs.getChannel().getY());

			outputs = processor.getChannelDataForA(parameter, input);
			input.getChannel().setA(outputs.getChannel().getA());

			outputs = processor.getChannelDataForBandMetric_b(parameter, input);
			input.getChannel().setB(outputs.getChannel().getB());
			input.setMetric(outputs.getMetric());

			outputs = processor.getChannelDataForC(parameter, input);

			System.out.println("Metric b: " + outputs.getMetric().getB());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
