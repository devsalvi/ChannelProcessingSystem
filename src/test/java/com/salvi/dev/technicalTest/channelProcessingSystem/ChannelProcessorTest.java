package com.salvi.dev.technicalTest.channelProcessingSystem;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ChannelProcessorTest {

    ChannelProcessor processor = new ChannelProcessor();

    @Test
    void testReadChannel() {
        List<String> lines = Arrays.asList("X, 1.0, 2.0, 3.0, 4.0, 5.0");
        List<Double> expected = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);
        List<Double> result = processor.readChannel("X", lines);
        assertEquals(expected, result);
    }

    @Test
    void testCalculateMetric() {
        List<Double> data = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);
        double expected = 3.0;
        double result = processor.calculateMetric("b", data);
        assertEquals(expected, result, 0.001);
    }

    @Test
    void testGetChannelDataForY() {
        List<Double> X = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);
        double m = 2.0;
        double c = 1.0;
        List<Double> expected = Arrays.asList(3.0, 5.0, 7.0, 9.0, 11.0);
        List<Double> result = processor.getChannelDataForY(X, m, c);
        assertEquals(expected, result);
    }

    @Test
    void testgetChannelDataForA() {
        List<Double> A = Arrays.asList(1.0, 0.5, 0.333, 0.25, 0.2);
        List<Double> Y = Arrays.asList(3.0, 5.0, 7.0, 9.0, 11.0);
        List<Double> expected = Arrays.asList(4.0, 5.5, 7.333, 9.25, 11.2);
        List<Double> result = processor.getChannelDataForB(A, Y);
        assertEquals(expected, result);
    }

    @Test
    void testgetChannelDataForB() {
        List<Double> X = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);
        List<Double> expected = Arrays.asList(1.0, 0.5, 0.3333333333333333, 0.25, 0.2);
        List<Double> result = processor.getChannelDataForA(X);
        assertEquals(expected, result);
    }

    @Test
    void testGetChannelDataForC() {
        List<Double> X = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);
        double b = 3.0;        
        List<Double> expected = Arrays.asList(4.0, 5.0, 6.0, 7.0, 8.0);
        List<Double> result = processor.getChannelDataForC(X, b);
        assertEquals(expected, result);
    }
}