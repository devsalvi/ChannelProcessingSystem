package com.salvi.dev.cps.service;

import java.util.stream.Collectors;

/**
 * Channel Processor A implementation
 */
public class ChannelProcessorAImpl extends ChannelProcessor {
    /**
     * Function to calculate A = 1/X
     * 
     * @param parameters
     * @param inputs
     * @return Output with channel A
     */
    @Override
    public Output function(Parameter parameters, Input inputs) {
        Output outputs = new Output();
        outputs.getChannel()
                .setA(inputs.getChannel().getX().stream().map(x -> (1 / x))
                        .collect(Collectors.toList()));
        return outputs;
    }

}
