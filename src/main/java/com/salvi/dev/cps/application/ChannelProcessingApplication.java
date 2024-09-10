package com.salvi.dev.cps.application;

// import static com.salvi.dev.cps.service.ChannelProcessor.writeChannel;//To be implemented
import static com.salvi.dev.cps.service.ChannelProcessor.readChannel;
import static com.salvi.dev.cps.service.ChannelProcessor.readParameters;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.salvi.dev.cps.service.ChannelProcessor;
import com.salvi.dev.cps.service.ChannelProcessorAImpl;
import com.salvi.dev.cps.service.ChannelProcessorBImpl;
import com.salvi.dev.cps.service.ChannelProcessorCImpl;
import com.salvi.dev.cps.service.ChannelProcessorYImpl;
import com.salvi.dev.cps.service.Input;
import com.salvi.dev.cps.service.Output;
import com.salvi.dev.cps.service.Parameter;

@SpringBootApplication
public class ChannelProcessingApplication {

	public static void main(String[] args) {

		Input input = new Input();
		Parameter parameter = new Parameter();
		Output outputF1, outputF2, outputF3, outputF4 = new Output(); // outputF4 is unused in this example

		ChannelProcessor processor;

		try {
			// Read input channels from channels.txt
			List<String> channelLines = Files.readAllLines(Paths.get(args.length > 0 ? args[0] : "channels.txt"));
			input.getChannel().setX(readChannel("X", channelLines));

			// Read parameters from parameters.txt
			List<String> paramLines = Files.readAllLines(Paths.get(args.length > 1 ? args[1] : "parameters.txt"));
			parameter = readParameters(paramLines);

			// Process channels using the defined functions
			processor = new ChannelProcessorYImpl();
			outputF1 = processor.function(parameter, input);
			input.getChannel().setY(outputF1.getChannel().getY());

			processor = new ChannelProcessorAImpl();
			outputF3 = processor.function(parameter, input);
			input.getChannel().setA(outputF3.getChannel().getA());

			processor = new ChannelProcessorBImpl();
			outputF2 = processor.function(parameter, input);
			input.getChannel().setB(outputF2.getChannel().getB());
			input.setMetric(outputF2.getMetric());

			processor = new ChannelProcessorCImpl();
			outputF4 = processor.function(parameter, input);// unused function4 output

			System.out.println("Metric b: " + outputF2.getMetric().getB());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
