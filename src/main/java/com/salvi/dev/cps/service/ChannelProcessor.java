package com.salvi.dev.cps.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ChannelProcessor {

    Outputs outputs;

    public ChannelProcessor() {
        outputs = new Outputs();
    }

    public List<Double> readChannel(String name, List<String> lines) {
        for (String line : lines) {
            if (line.startsWith(name)) {
                String[] parts = line.split(name)[1].trim().split(",");
                return Arrays.stream(parts)
                        .filter(part -> !part.isEmpty())
                        .map(Double::parseDouble)
                        .collect(Collectors.toList());
            }
        }
        throw new IllegalArgumentException("Channel " + name + " not found in channels.txt");
    }

    public void writeChannel(String name, List<Double> data) {
        // Placeholder for writing channel data
        System.out.println("Channel " + name + ": " + data);
    }

    public Parameter readParameters(List<String> paramLines) {
        Parameter parameter = new Parameter();
        String param1 = paramLines.getFirst().split(",")[0].trim().toLowerCase();
        String param2 = paramLines.get(1).split(",")[0].trim().toLowerCase();
        double double1 = Double.parseDouble(paramLines.getFirst().split(",")[1].trim());
        double double2 = Double.parseDouble(paramLines.get(1).split(",")[1].trim());

        setParameter(param1, double1, parameter);
        setParameter(param2, double2, parameter);
        return parameter;
    }

    public Metric calculateMetric(String name, List<Double> data) {
        // Placeholder for calculating metrics
        Metric metric = new Metric();
        switch (name.toLowerCase()) {
            case "b":
                metric.setB(data.stream().mapToDouble(Double::doubleValue).average().orElse(0.0));
                break;
            default:
                throw new IllegalArgumentException("Invalid metric name: " + name);
        }
        return metric;
    }

    public Outputs getChannelDataForA(Parameter parameters, Input inputs) {
        outputs.getChannel()
                .setA(inputs.getChannel().getX().stream().map(x -> (1/x))
                        .collect(Collectors.toList()));
        return outputs;
    }

    public Outputs getChannelDataForBandMetric_b(Parameter parameters, Input inputs) {
       try {
            List<Double> B = inputs.getChannel().getA().stream()
                    .map(a -> a + inputs.getChannel().getY().get(inputs.getChannel().getA().indexOf(a)))
                    .collect(Collectors.toList());
            Metric metricB = calculateMetric("b", B);
            outputs.getChannel().setB(B);
            outputs.setMetric(metricB);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Channel A or Y is empty");
        }
        return outputs;
    }

    public Outputs getChannelDataForY(Parameter parameters, Input inputs) {
        List<Double> Y = inputs.getChannel().getX().stream().map(x -> parameters.getM() * x + parameters.getC())
                .collect(Collectors.toList());
        outputs.getChannel().setY(Y);
        return outputs;
    }

    public Outputs getChannelDataForC(Parameter parameters, Input inputs) {
        List<Double> C = inputs.getChannel().getX().stream().map(x -> x + inputs.getMetric().getB())
                .collect(Collectors.toList());
        outputs.getChannel().setC(C);
        return outputs;
    }

    private void setParameter(String param, double value, Parameter parameter) {
        switch (param) {
            case "m":
                parameter.setM(value);
                break;
            case "c":
                parameter.setC(value);
                break;
            default:
                break;
        }
    }
}