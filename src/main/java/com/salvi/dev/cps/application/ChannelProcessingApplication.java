package com.salvi.dev.cps.application;

import java.util.List;
import java.util.Scanner;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.salvi.dev.cps.service.ChannelProcessorAImpl;
import com.salvi.dev.cps.service.ChannelProcessorBImpl;
import com.salvi.dev.cps.service.ChannelProcessorCImpl;
import com.salvi.dev.cps.service.ChannelProcessorUtils;
import com.salvi.dev.cps.service.ChannelProcessorYImpl;
import com.salvi.dev.cps.service.Input;
import com.salvi.dev.cps.service.Metric;
import com.salvi.dev.cps.service.Output;
import com.salvi.dev.cps.service.Parameter;

@SpringBootApplication
public class ChannelProcessingApplication {

	private static Input input;
	private static Parameter parameters;
	private static Output outputFn1;
	private static List<Double> Y;
	private static List<Double> A;
	private static Output outputFn2;
	private static Output outputFn3;
	private static Output outputFn4;
	private static Metric b;
	private static List<Double> C;
	private static List<Double> B;

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Channel Processing System");
		boolean continueProcessing = true;
		try {

			System.out.println(
					"Enter the name of the file containing the channels (press enter for default channels.txt): ");
			String channelsFile = scanner.nextLine();
			System.out.println(
					"Enter the name of the file containing the parameters (press enter for default parameters.txt): ");
			String parametersFile = scanner.nextLine();
			while (continueProcessing) {
				System.out.println("Select an operation: ");
				System.out.println("1. Execute function 1 -> 𝑌 = 𝑚𝑋 + 𝑐");
				System.out.println("2. Execute function 2 -> 𝐵 = 𝐴 + 𝑌 𝑏 = 𝑚𝑒𝑎𝑛(𝐵)");
				System.out.println("3. Execute function 3 -> 𝐴 = 1/𝑋");
				System.out.println("4. Execute function 4 -> 𝐶 = 𝑋 + 𝑏");
				System.out.println("5. Calculate metric b");
				System.out.println("6. Use function order:");
				int operation = Integer.parseInt(scanner.nextLine());

				ChannelProcessorUtils channelProcessorUtil = new ChannelProcessorUtils(); // ChannelProcessorUtil to
																							// help
																							// calculate metrics, here
																							// calculating b

				input = channelProcessorUtil.setBaseInput(channelsFile);
				parameters = channelProcessorUtil.setBaseParameter(parametersFile);

				switch (operation) {
					case 1:
						outputFn1 = new ChannelProcessorYImpl().function(parameters, input);
						Y = outputFn1.getChannel().getY();
						System.out.println("\nOutput: " + outputFn1.toString());
						break;
					case 2:
						if (Y.isEmpty()) {
							throw new Exception("Channel Y is not calculated yet");
						} else {
							input.getChannel().setY(Y); // Set channel Y as input for function2
						}
						if (A.isEmpty()) {
							throw new Exception("Channel A is not calculated yet");
						} else {
							input.getChannel().setA(A); // Set channel A as input for function2
						}

						outputFn2 = new ChannelProcessorBImpl().function(parameters, input); // Calculate metric b using
																								// function2

						b = outputFn2.getMetric();
						B = outputFn2.getChannel().getB();
						System.out.println("\nOutput: " + outputFn2.toString());
						break;
					case 3:
						outputFn3 = new ChannelProcessorAImpl().function(parameters, input);
						A = outputFn3.getChannel().getA();
						System.out.println("\nOutput: " + outputFn3.toString());
						break;
					case 4:
						if (b.getB() == 0.0) {
							throw new Exception("Metric b is not calculated yet");
						} else {
							input.getMetric().setB(b.getB());
						}
						outputFn4 = new ChannelProcessorCImpl().function(parameters, input);
						System.out.println("\nOutput: " + outputFn4.toString());
						break;
					case 5:
						String[] calculateMetricBFunctionOrder = { "f3", "f1", "f2" };
						processFunctionOrder(calculateMetricBFunctionOrder);
						System.out.println("Metric b: " + b.getB());
						break;
					case 6:
						System.out.println("Enter the function order: e.g\"f1, f3, f2, f4\"");
						String functionOrder = scanner.nextLine();
						System.out.println("Function Order:" + functionOrder);
						String[] functions = functionOrder.split(",");
						processFunctionOrder(functions);
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
		} catch (Exception e) {
			System.out.println("Error occurred while processing the channels: " + e.getMessage());
		}

	}

	private static void processFunctionOrder(String[] functions) throws Exception {
		for (String function : functions) {
			switch (function.trim()) {
				case "f1":
					outputFn1 = new ChannelProcessorYImpl().function(parameters, input);
					Y = outputFn1.getChannel().getY();
					System.out.println("\nOutput: " + outputFn1.toString());
					break;
				case "f2":
					if (Y.isEmpty()) {
						throw new Exception("Channel Y is not calculated yet");
					} else {
						input.getChannel().setY(Y); // Set channel Y as input for function2
					}
					if (A.isEmpty()) {
						throw new Exception("Channel A is not calculated yet");
					} else {
						input.getChannel().setA(A); // Set channel A as input for function2
					}

					outputFn2 = new ChannelProcessorBImpl().function(parameters, input); // Calculate
																							// metric
																							// b using
																							// function2

					b = outputFn2.getMetric();
					B = outputFn2.getChannel().getB();
					System.out.println("\nOutput: " + outputFn2.toString());
					break;
				case "f3":
					outputFn3 = new ChannelProcessorAImpl().function(parameters, input);
					A = outputFn3.getChannel().getA();
					System.out.println("\nOutput: " + outputFn3.toString());
					break;
				case "f4":
					if (b.getB() == 0.0) {
						throw new Exception("Metric b is not calculated yet");
					} else {
						input.getMetric().setB(b.getB());
					}
					outputFn4 = new ChannelProcessorCImpl().function(parameters, input);
					C = outputFn4.getChannel().getC();
					System.out.println("\nOutput: " + outputFn4.toString());
					break;
				default:
					System.out.println("Invalid function");
			}
		}
	}

}
