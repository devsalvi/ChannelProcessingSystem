package com.salvi.dev.technicalTest.channelProcessingSystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChannelProcessingSystem {


	public static void main(String[] args) {
		ChannelProcessor processor = new ChannelProcessor();

		try {
			// Read input channels from channels.txt
			List<String> channelLines = Files.readAllLines(Paths.get("channels.txt"));
			List<Double> X = processor.readChannel("X", channelLines);

			// Read parameters from parameters.txt
			List<String> paramLines = Files.readAllLines(Paths.get("parameters.txt"));
			double m = Double.parseDouble(paramLines.get(0).split(",")[1].trim());
			double c = Double.parseDouble(paramLines.get(1).split(",")[1].trim());

			// Process channels using the defined functions
			List<Double> Y = processor.getChannelDataForY(X, m, c);
			List<Double> A = processor.getChannelDataForA(X);
			List<Double> B = processor.getChannelDataForB(A, Y);
			double b = processor.calculateMetric("b", B);
			List<Double> C = processor.getChannelDataForC(X, b);

			// Write output channels and metrics
			// processor.writeChannel("Y", Y);
			// processor.writeChannel("A", A);
			// processor.writeChannel("B", B);

			System.out.println("Metric b: " + b);
			

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
