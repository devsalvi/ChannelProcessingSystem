package com.salvi.dev.cps.service;

import java.util.List;
import java.util.stream.Collectors;

public class ChannelProcessorBImpl extends ChannelProcessor {
    @Override
    public Output function(Parameter parameters, Input inputs) {
        Output outputs = new Output();
        try {
            List<Double> B = inputs.getChannel().getA().stream()
                    .map(a -> a + inputs.getChannel().getY().get(inputs.getChannel().getA().indexOf(a)))
                    .collect(Collectors.toList());
            Metric metricB = calculateMetric(B);
            outputs.getChannel().setB(B);
            outputs.setMetric(metricB);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Channel A or Y is empty");
        }
        return outputs;
    }

    private Metric calculateMetric(List<Double> data) {
        Metric metric = new Metric();
        metric.setB(data.stream().mapToDouble(Double::doubleValue).average().orElse(0.0));
        return metric;
    }
}
