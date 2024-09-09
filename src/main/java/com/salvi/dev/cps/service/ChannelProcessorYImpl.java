package com.salvi.dev.cps.service;

import java.util.List;
import java.util.stream.Collectors;

public class ChannelProcessorYImpl extends ChannelProcessor {
    @Override
    public Output function(Parameter parameters, Input inputs) {
        Output outputs = new Output();
        List<Double> Y = inputs.getChannel().getX().stream().map(x -> parameters.getM() * x + parameters.getC())
                .collect(Collectors.toList());
        outputs.getChannel().setY(Y);
        return outputs;
    }

}
