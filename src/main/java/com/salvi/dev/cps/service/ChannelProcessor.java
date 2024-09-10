package com.salvi.dev.cps.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Abstract class to process channels
 */
public abstract class ChannelProcessor {
    public abstract Output function(Parameter parameters, Input inputs);

    /**
     * @param name
     * @param lines
     * @return channel data
     */
    public static List<Double> readChannel(String name, List<String> lines) {
        List<Double> channel = new ArrayList<>();
        for (String line : lines) {
            if (line.startsWith(name)) {
                String[] parts = line.split(name)[1].trim().split(",");
                channel = Arrays.stream(parts)
                        .filter(part -> !part.isEmpty())
                        .map(Double::parseDouble)
                        .collect(Collectors.toList());
            } else {
                throw new IllegalArgumentException("Invalid channel name");
            }
        }
        return channel;
    }

    public static void writeChannel(String name, List<Double> data) {
        // Placeholder for writing channel data
        System.out.println("Channel " + name + ": " + data);
    }

    /**
     * @param paramLines
     * @return parameters m and c
     */
    public static Parameter readParameters(List<String> paramLines) {
        Parameter parameter = new Parameter();
        String param1 = paramLines.getFirst().split(",")[0].trim().toLowerCase();
        String param2 = paramLines.get(1).split(",")[0].trim().toLowerCase();
        double double1 = Double.parseDouble(paramLines.getFirst().split(",")[1].trim());
        double double2 = Double.parseDouble(paramLines.get(1).split(",")[1].trim());

        setParameter(param1, double1, parameter);
        setParameter(param2, double2, parameter);
        return parameter;
    }

    /**
     * Function to parse m and c from parameters.txt
     * 
     * @param param
     * @param value
     * @param parameter
     */
    private static void setParameter(String param, double value, Parameter parameter) {
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
