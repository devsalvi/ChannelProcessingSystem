package com.salvi.dev.technicalTest.channelProcessingSystem;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ChannelProcessor {

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

    public double calculateMetric(String name, List<Double> data) {
        // Placeholder for calculating metrics
        return data.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    public List<Double> getChannelDataForY(List<Double> X, double m, double c) {
        return X.stream().map(x -> m * x + c).collect(Collectors.toList());
    }

    public List<Double> getChannelDataForB(List<Double> A, List<Double> Y) {
        List<Double> B = A.stream().map(a -> a + Y.get(A.indexOf(a))).collect(Collectors.toList());
        // double b = calculateMetric("b", B);
        return B;
    }

    public List<Double> getChannelDataForA(List<Double> X) {
        return X.stream().map(x -> 1 / x).collect(Collectors.toList());
    }

    public List<Double> getChannelDataForC(List<Double> X, double b) {
        return X.stream().map(x -> x + b).collect(Collectors.toList());
    }
}