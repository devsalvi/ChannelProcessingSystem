package com.salvi.dev.cps.service;

import java.util.stream.Collectors;

public class ChannelProcessorAImpl extends ChannelProcessor {
    @Override
    public Output function(Parameter parameters, Input inputs) {
        Output outputs = new Output();
        outputs.getChannel()
                .setA(inputs.getChannel().getX().stream().map(x -> (1/x))
                        .collect(Collectors.toList()));
        return outputs;
    }

}
