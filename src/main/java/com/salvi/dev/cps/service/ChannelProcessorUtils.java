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
}
