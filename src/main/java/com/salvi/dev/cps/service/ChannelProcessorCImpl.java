package com.salvi.dev.cps.service;

import java.util.List;
import java.util.stream.Collectors;

public class ChannelProcessorCImpl extends ChannelProcessor {
    @Override
    public Output function(Parameter parameters, Input inputs) {
        Output outputs = new Output();
        List<Double> C = inputs.getChannel().getX().stream().map(x -> x + inputs.getMetric().getB())
                .collect(Collectors.toList());
        outputs.getChannel().setC(C);
        return outputs;
    }

}
