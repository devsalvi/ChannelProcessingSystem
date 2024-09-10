package com.salvi.dev.cps.application;

import java.util.Scanner;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.salvi.dev.cps.service.ChannelProcessorUtils;
import com.salvi.dev.cps.service.Output;

@SpringBootApplication
public class ChannelProcessingApplication {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Channel Processing System");
		boolean continueProcessing = true;
		while (continueProcessing) {

			System.out.println(
					"Enter the name of the file containing the channels (press enter for default channels.txt): ");
			String channelsFile = scanner.nextLine();
			System.out.println(
					"Enter the name of the file containing the parameters (press enter for default parameters.txt): ");
			String parametersFile = scanner.nextLine();
			System.out.println("Select an operation: ");
			System.out.println("1. Execute function 1");
			System.out.println("2. Execute function 2");
			System.out.println("3. Execute function 3");
			System.out.println("4. Execute function 4");
			System.out.println("5. Calculate metric b");
			int operation = Integer.parseInt(scanner.nextLine());

			ChannelProcessorUtils channelProcessorUtil = new ChannelProcessorUtils(); // ChannelProcessorUtil to help
																					// calculate metrics, here
																					// calculating b
			switch (operation) {
				case 1:
					Output outputFn1 = channelProcessorUtil.executeFuntion1(channelsFile,
							parametersFile);
					System.out.println("Function 1 output: " + outputFn1);
					break;
				case 2:
					Output outputFn2 = channelProcessorUtil.executeFuntion2(channelsFile,
							parametersFile);
					System.out.println("Function 2 output: " + outputFn2);
					break;
				case 3:
					Output outputFn3 = channelProcessorUtil.executeFuntion3(channelsFile,
							parametersFile);
					System.out.println("Function 3 output: " + outputFn3);
					break;
				case 4:
					Output outputFn4 = channelProcessorUtil.executeFuntion4(channelsFile,
							parametersFile);
					System.out.println("Function 4 output: " + outputFn4);
					break;
				case 5:
					double metric_b = channelProcessorUtil.calculateMetric_b(channelsFile,
							parametersFile);
					System.out.println("Metric b: " + metric_b);
					break;
				default:
					System.out.println("Invalid operation");
			}
			System.out.println("Press any key to continue processing, N to exit application");
			String continueProcessingStr = scanner.nextLine();
			if (continueProcessingStr.equalsIgnoreCase("N")) {
				continueProcessing = false;
			}
		}
		scanner.close();

	}

}
