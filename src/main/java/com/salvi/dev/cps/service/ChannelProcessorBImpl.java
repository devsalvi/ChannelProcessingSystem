package com.salvi.dev.cps.service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Channel Processor B implementation
 */
public class ChannelProcessorBImpl extends ChannelProcessor {
    /**
     * Function to calculate B = A + Y
     * 
     * @param parameters
     * @param inputs
     * @return Output with channel B and metric b
     */
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

    /**
     * Function to calculate metric b
     * 
     * @param data
     * @return metric b
     */
    private Metric calculateMetric(List<Double> data) {
        Metric metric = new Metric();
        double b = data.stream().mapToDouble(Double::doubleValue).average().orElse(0.0); // Calculate mean of channel B  
        metric.setB(b);   
        return metric;
    }
}
