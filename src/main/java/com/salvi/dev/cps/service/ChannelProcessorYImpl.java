package com.salvi.dev.cps.service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Channel Processor Y implementation
 */
public class ChannelProcessorYImpl extends ChannelProcessor {
    /**
     * Function to calculate Y = mX + c
     * 
     * @param parameters
     * @param inputs
     * @return Output with channel Y
     */
    @Override
    public Output function(Parameter parameters, Input inputs) {
        Output outputs = new Output();
        List<Double> Y = inputs.getChannel().getX().stream().map(x -> parameters.getM() * x + parameters.getC())
                .collect(Collectors.toList());
        outputs.getChannel().setY(Y);
        return outputs;
    }

}
