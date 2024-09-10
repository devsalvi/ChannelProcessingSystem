package com.salvi.dev.cps.application;

import static com.salvi.dev.cps.service.ChannelProcessor.readChannel;
import static com.salvi.dev.cps.service.ChannelProcessor.readParameters;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.salvi.dev.cps.service.ChannelProcessor;
import com.salvi.dev.cps.service.ChannelProcessorAImpl;
import com.salvi.dev.cps.service.ChannelProcessorBImpl;
import com.salvi.dev.cps.service.ChannelProcessorCImpl;
import com.salvi.dev.cps.service.ChannelProcessorYImpl;
import com.salvi.dev.cps.service.Input;
import com.salvi.dev.cps.service.Metric;
import com.salvi.dev.cps.service.Output;
import com.salvi.dev.cps.service.Parameter;

public class MBAMGApplication {
    public Metric getMetric() {
        Input input = new Input();
        Parameter parameter = new Parameter();
        // Output outputF1, outputF2, outputF3 = new Output(), outputF4 = new Output();
        Metric outputMetric = new Metric();
        ChannelProcessor processor;

        try {
            // Read input channels from channels.txt
            List<String> channelLines = Files.readAllLines(Paths.get("channels.txt"));
            input.getChannel().setX(readChannel("X", channelLines));

            // Read parameters from parameters.txt
            List<String> paramLines = Files.readAllLines(Paths.get("parameters.txt"));
            parameter = readParameters(paramLines);

            // // Process channels using the defined functions
            // processor = new ChannelProcessorYImpl();
            // outputF1 = processor.function(parameter, input);
            // input.getChannel().setY(outputF1.getChannel().getY());

            // processor = new ChannelProcessorAImpl();
            // outputF2 = processor.function(parameter, input);
            // input.getChannel().setA(outputF2.getChannel().getA());

            // processor = new ChannelProcessorBImpl();
            // outputF3 = processor.function(parameter, input);
            // input.getChannel().setB(outputF3.getChannel().getB());
            // input.setMetric(outputF3.getMetric());

            // processor = new ChannelProcessorCImpl();
            // outputF4 = processor.function(parameter, input);// unused function4 output
            outputMetric = getMetric(channelLines, paramLines);

            System.out.println("Metric b: " + outputMetric.getB());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputMetric;
    }

    public Metric getMetric(List<String> channelLines, List<String> paramLines) {
        Input input = new Input();
        Parameter parameter = new Parameter();
        Output outputF1, outputF2, outputF3 = new Output(), outputF4 = new Output();
        ChannelProcessor processor;

        input.getChannel().setX(readChannel("X", channelLines));
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

        return outputF3.getMetric();
    }
}
