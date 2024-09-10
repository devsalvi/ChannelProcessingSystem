package com.salvi.dev.cps;

import static com.salvi.dev.cps.service.ChannelProcessor.readChannel;
import static com.salvi.dev.cps.service.ChannelProcessor.readParameters;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.salvi.dev.cps.service.ChannelProcessor;
import com.salvi.dev.cps.service.ChannelProcessorAImpl;
import com.salvi.dev.cps.service.ChannelProcessorBImpl;
import com.salvi.dev.cps.service.ChannelProcessorCImpl;
import com.salvi.dev.cps.service.ChannelProcessorYImpl;
import com.salvi.dev.cps.service.Input;
import com.salvi.dev.cps.service.Metric;
import com.salvi.dev.cps.service.Output;
import com.salvi.dev.cps.service.Parameter;

// Test cases for ChannelProcessor.java
class ChannelProcessorTest {

    ChannelProcessor processor;
    Parameter parameter = new Parameter();
    Metric metric = new Metric();
    Input channelInput = new Input();

    /*
     * Test case to read channel data
     */
    @Test
    void testReadChannel() {
        List<String> lines = Arrays.asList("X, 1.0, 2.0, 3.0, 4.0, 5.0");
        List<Double> expected = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);
        List<Double> result = readChannel("X", lines);
        assertEquals(expected, result);
    }

    /*
     * Test case to read channel data with invalid input
     */
    @Test
    void testReadChannel_InvalidInput() {
        List<String> lines = Arrays.asList("G, 1.0, 2.0, 3.0, 4.0, 5.0");
        assertThrows(IllegalArgumentException.class, () -> readChannel("X", lines));
    }

    /*
     * Test case to read parameters method
     */
    @Test
    void testReadParameters() {
        List<String> paramLines = Arrays.asList("M,2.0", "C,1.0");
        Parameter expected = new Parameter();
        expected.setM(2.0);
        expected.setC(1.0);
        Parameter result = readParameters(paramLines);
        assertEquals(expected.getM(), result.getM());
        assertEquals(expected.getC(), result.getC());
    }

    /*
     * Test case to read parameters method with switched input
     */
    @Test
    void testReadParameters_Switched() {
        List<String> paramLines = Arrays.asList("C,2.0", "M,1.0");
        Parameter expected = new Parameter();
        expected.setM(1.0);
        expected.setC(2.0);
        Parameter result = readParameters(paramLines);
        assertEquals(expected.getM(), result.getM());
        assertEquals(expected.getC(), result.getC());
    }

    /*
     * Test case to function1 with output channel Y
     */
    @Test
    void testGetChannelDataForY() {
        List<Double> X = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);
        channelInput.getChannel().setX(X);
        parameter.setM(2.0);
        parameter.setC(1.0);
        List<Double> expected = Arrays.asList(3.0, 5.0, 7.0, 9.0, 11.0);
        processor = new ChannelProcessorYImpl();
        Output result = processor.function(parameter, channelInput);
        assertEquals(expected, result.getChannel().getY());
    }

    /*
     * Test case to function1 with output channel B and metric b
     */
    @Test
    void testgetChannelDataForB() {
        List<Double> X = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);
        channelInput.getChannel().setX(X);
        parameter.setM(2.0);
        parameter.setC(1.0);
        List<Double> A = Arrays.asList(4.0, 5.0, 1.0, 4.0, 9.0);
        channelInput.getChannel().setA(A);
        List<Double> Y = Arrays.asList(14.0, 15.0, 11.0, 14.0, 19.0);
        channelInput.getChannel().setY(Y);
        List<Double> expected = Arrays.asList(18.0, 20.0, 12.0, 18.0, 28.0);
        processor = new ChannelProcessorBImpl();
        Output result = processor.function(parameter, channelInput);
        Double expectedMetric_b = 19.2;
        assertEquals(expectedMetric_b, result.getMetric().getB());
        assertEquals(expected, result.getChannel().getB());
    }

    /*
     * Test case to function2 with output channel B and metric b with empty input
     */
    @Test
    void testGetChannelDataForB_WithEmptyInput() {
        processor = new ChannelProcessorBImpl();
        assertThrows(IllegalArgumentException.class,
                () -> processor.function(parameter, channelInput));
    }

    /*
     * Test case to function3 with output channel A
     */
    @Test
    void testgetChannelDataForA() {
        List<Double> X = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);
        channelInput.getChannel().setX(X);
        parameter.setM(2.0);
        parameter.setC(1.0);
        List<Double> expectedChannelA = Arrays.asList(1.0, 0.5, 0.3333333333333333, 0.25, 0.20);
        processor = new ChannelProcessorAImpl();
        Output result = processor.function(parameter, channelInput);
        assertEquals(expectedChannelA, result.getChannel().getA());
    }

    /*
     * Test case to function3 with output channel A with empty input
     */
    @Test
    void testGetChannelDataForA_WithEmptyInput() {
        channelInput.getChannel().setX(Arrays.asList());
        parameter.setM(2.0);
        parameter.setC(1.0);
        List<Double> expected = Arrays.asList();
        processor = new ChannelProcessorAImpl();
        Output result = processor.function(parameter, channelInput);
        assertEquals(expected, result.getChannel().getA());
    }

    /*
     * Test case to function4 with output channel C
     */
    @Test
    void testGetChannelDataForC() {
        List<Double> X = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);
        channelInput.getChannel().setX(X);
        channelInput.getMetric().setB(3.0);
        List<Double> expected = Arrays.asList(4.0, 5.0, 6.0, 7.0, 8.0);
        processor = new ChannelProcessorCImpl();
        Output result = processor.function(parameter, channelInput);
        assertEquals(expected, result.getChannel().getC());
    }

    /*
     * Test case to function1 with output channel C with empty input
     */
    @Test
    void testGetChannelDataForC_WithEmptyInput() {
        List<Double> X = Arrays.asList();
        channelInput.getChannel().setX(X);
        double b = 3.0;
        channelInput.getMetric().setB(b);
        List<Double> expected = Arrays.asList();
        processor = new ChannelProcessorCImpl();
        Output result = processor.function(parameter, channelInput);
        assertEquals(expected, result.getChannel().getC());
    }
}