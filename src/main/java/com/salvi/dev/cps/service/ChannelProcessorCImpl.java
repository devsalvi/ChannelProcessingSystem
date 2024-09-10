package com.salvi.dev.cps.service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Channel Processor C implementation
 */
public class ChannelProcessorCImpl extends ChannelProcessor {
    /**
     * Function to calculate C = X + b
     * 
     * @param parameters
     * @param inputs
     * @return Output with channel C
     */
    @Override
    public Output function(Parameter parameters, Input inputs) {
        Output outputs = new Output();
        List<Double> C = inputs.getChannel().getX().stream().map(x -> x + inputs.getMetric().getB())
                .collect(Collectors.toList());
        outputs.getChannel().setC(C);
        return outputs;
    }

}
