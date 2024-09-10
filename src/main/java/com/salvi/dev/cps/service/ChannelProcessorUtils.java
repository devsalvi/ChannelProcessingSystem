package com.salvi.dev.cps.service;

import static com.salvi.dev.cps.service.ChannelProcessor.readChannel;
import static com.salvi.dev.cps.service.ChannelProcessor.readParameters;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/*
 * Channel Processor Util class is a helper class to calculate metrics
 */
public class ChannelProcessorUtils {
    /**
     * Function to calculate metric b
     * 
     * @param channelsFileStr   - file path of channels.txt
     * @param parametersFileStr - file path of parameters.txt
     * @return metric b
     * @throws Exception
     */
    public double calculateMetric_b(String channelsFileStr, String parametersFileStr) throws Exception {

        double b = 0.0;
        b = executeFuntion2(channelsFileStr, parametersFileStr).getMetric().getB();
        return b;
    }

    public Output executeFuntion1(String channelsFileStr, String parametersFileStr) throws Exception {
        Output outputFn1 = new Output();

        Parameter parameters = setBaseParameter(parametersFileStr);
        Input input = setBaseInput(channelsFileStr);

        outputFn1 = new ChannelProcessorYImpl().function(parameters, input);
        System.out.println("\nOutput: ");
        return outputFn1;
    }

    public Output executeFuntion2(String channelsFileStr, String parametersFileStr) throws Exception {
        List<Double> Y, A;
        Output outputFn2 = new Output();

        // Read parameters from parameters.txt
        Parameter parameters = setBaseParameter(parametersFileStr);
        Input input = setBaseInput(channelsFileStr);

        Y = new ChannelProcessorYImpl().function(parameters, input).getChannel().getY(); // Calculate channel Y
                                                                                         // using function1
        A = new ChannelProcessorAImpl().function(parameters, input).getChannel().getA(); // Calculate channel A
                                                                                         // using function3

        input.getChannel().setY(Y); // Set channel Y as input for function2
        input.getChannel().setA(A); // Set channel A as input for function2

        outputFn2 = new ChannelProcessorBImpl().function(parameters, input); // Calculate metric b using
                                                                             // function2
        System.out.println("\nOutput: ");
        return outputFn2;
    }

    public Output executeFuntion3(String channelsFileStr, String parametersFileStr) throws Exception {
        Output outputFn3 = new Output();

        Parameter parameters = setBaseParameter(parametersFileStr);
        Input input = setBaseInput(channelsFileStr);

        outputFn3 = new ChannelProcessorAImpl().function(parameters, input);
        System.out.println("\nOutput: ");
        return outputFn3;
    }

    public Output executeFuntion4(String channelsFileStr, String parametersFileStr) throws Exception {
        Output outputFn4 = new Output();

        Parameter parameters = setBaseParameter(parametersFileStr);
        Input input = setBaseInput(channelsFileStr);

        double b = 0.0;
        b = executeFuntion2(channelsFileStr, parametersFileStr).getMetric().getB();

        input.getMetric().setB(b);

        outputFn4 = new ChannelProcessorCImpl().function(parameters, input);
        System.out.println("\nOutput: ");
        return outputFn4;
    }

    public Input setBaseInput(String channelsFileStr) throws Exception {
        Input input = new Input();
        try {
            List<String> channelLines = Files
                    .readAllLines(Paths.get(channelsFileStr.isBlank() ? "channels.txt" : channelsFileStr));
            List<Double> X = readChannel("X", channelLines);
            input.getChannel().setX(X);
        } catch (IllegalArgumentException | IOException e) {
            e.printStackTrace();
            throw e;
        }
        return input;
    }

    public Input setBaseInput(List<String> channelLines) throws Exception {
        Input input = new Input();
        try {
            List<Double> X = readChannel("X", channelLines);
            input.getChannel().setX(X);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw e;
        }
        return input;
    }

    public Parameter setBaseParameter(List<String> paramLines) throws Exception {
        Parameter parameter = new Parameter();
        try {
            parameter = readParameters(paramLines);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw e;
        }
        return parameter;
    }

    public Parameter setBaseParameter(String parametersFileStr) throws Exception {
        Parameter parameter = new Parameter();
        try {
            List<String> paramLines = Files
                    .readAllLines(Paths.get(parametersFileStr.isBlank() ? "parameters.txt" : parametersFileStr));
            parameter = readParameters(paramLines);
        } catch (IllegalArgumentException | IOException e) {
            e.printStackTrace();
            throw e;
        }
        return parameter;
    }

    public double calculateMetricB(List<String> channelLines, List<String> paramLines) throws Exception {
        double b = 0.0;
        b = executeFunction2(channelLines, paramLines).getMetric().getB();
        return b;
    }

    public Output executeFunction4(List<String> channelLines, List<String> paramLines) throws Exception {
        Output outputFn4 = new Output();

        Parameter parameters = setBaseParameter(paramLines);
        Input input = setBaseInput(channelLines);

        double b = 0.0;
        b = executeFunction2(channelLines, paramLines).getMetric().getB();

        input.getMetric().setB(b);

        outputFn4 = new ChannelProcessorCImpl().function(parameters, input);
        System.out.println("\nOutput: ");
        return outputFn4;
    }

    public Output executeFunction3(List<String> channelLines, List<String> paramLines) throws Exception {
        Output outputFn3 = new Output();

        Parameter parameters = setBaseParameter(paramLines);
        Input input = setBaseInput(channelLines);

        outputFn3 = new ChannelProcessorAImpl().function(parameters, input);
        System.out.println("\nOutput: ");
        return outputFn3;
    }

    public Output executeFunction2(List<String> channelLines, List<String> paramLines) throws Exception {
        List<Double> Y, A;
        Output outputFn2 = new Output();

        // Read parameters from parameters.txt
        Parameter parameters = setBaseParameter(paramLines);
        Input input = setBaseInput(channelLines);

        Y = new ChannelProcessorYImpl().function(parameters, input).getChannel().getY(); // Calculate channel Y
                                                                                         // using function1
        A = new ChannelProcessorAImpl().function(parameters, input).getChannel().getA(); // Calculate channel A
                                                                                         // using function3

        input.getChannel().setY(Y); // Set channel Y as input for function2
        input.getChannel().setA(A); // Set channel A as input for function2

        outputFn2 = new ChannelProcessorBImpl().function(parameters, input); // Calculate metric b using
                                                                             // function2
        System.out.println("\nOutput: ");
        return outputFn2;
    }

    public Output executeFunction1(List<String> channelLines, List<String> paramLines) throws Exception {
        Output outputFn1 = new Output();

        Parameter parameters = setBaseParameter(paramLines);
        Input input = setBaseInput(channelLines);

        outputFn1 = new ChannelProcessorYImpl().function(parameters, input);
        System.out.println("\nOutput: ");
        return outputFn1;
    }
}
