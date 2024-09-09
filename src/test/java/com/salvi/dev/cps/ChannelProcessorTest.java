package com.salvi.dev.cps;

import org.junit.jupiter.api.Test;

import com.salvi.dev.cps.service.ChannelProcessor;
import com.salvi.dev.cps.service.Input;
import com.salvi.dev.cps.service.Metric;
import com.salvi.dev.cps.service.Outputs;
import com.salvi.dev.cps.service.Parameter;

import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ChannelProcessorTest {

    ChannelProcessor processor = new ChannelProcessor();
    Parameter parameter = new Parameter();
    Metric metric = new Metric();
    Input channelInput = new Input();

    @Test
    void testReadChannel() {
        List<String> lines = Arrays.asList("X, 1.0, 2.0, 3.0, 4.0, 5.0");
        List<Double> expected = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);
        List<Double> result = processor.readChannel("X", lines);
        assertEquals(expected, result);
    }

    @Test
    void testReadChannel_InvalidInput() {
        List<String> lines = Arrays.asList("G, 1.0, 2.0, 3.0, 4.0, 5.0");
        assertThrows(IllegalArgumentException.class, () -> processor.readChannel("X", lines));
    }

    @Test
    void testCalculateMetric() {
        List<Double> data = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);
        double expected = 3.0;
        Metric result = processor.calculateMetric("b", data);
        assertEquals(expected, result.getB(), 0.001);
    }

    @Test
    void testCalculateMetric_InvalidMetric() {
        List<Double> data = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);
        assertThrows(IllegalArgumentException.class, () -> processor.calculateMetric("z", data));
    }

    @Test
    void testGetChannelDataForY() {
        List<Double> X = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);
        channelInput.getChannel().setX(X);
        parameter.setM(2.0);
        parameter.setC(1.0);
        List<Double> expected = Arrays.asList(3.0, 5.0, 7.0, 9.0, 11.0);
        Outputs result = processor.getChannelDataForY(parameter, channelInput);
        assertEquals(expected, result.getChannel().getY());
    }

    @Test
    void testgetChannelDataForA() {
        List<Double> X = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);
        channelInput.getChannel().setX(X);
        parameter.setM(2.0);
        parameter.setC(1.0);
        List<Double> expectedChannelA = Arrays.asList(1.0, 0.5, 0.3333333333333333, 0.25, 0.20);
        Outputs result = processor.getChannelDataForA(parameter, channelInput);
        assertEquals(expectedChannelA, result.getChannel().getA());
    }

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
        Outputs result = processor.getChannelDataForBandMetric_b(parameter, channelInput);
        Double expectedMetric_b = 19.2;
        assertEquals(expectedMetric_b, result.getMetric().getB());
        assertEquals(expected, result.getChannel().getB());
    }

    @Test
    void testGetChannelDataForC() {
        List<Double> X = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);
        channelInput.getChannel().setX(X);
        channelInput.getMetric().setB(3.0);
        List<Double> expected = Arrays.asList(4.0, 5.0, 6.0, 7.0, 8.0);
        Outputs result = processor.getChannelDataForC(parameter, channelInput);
        assertEquals(expected, result.getChannel().getC());
    }

    @Test
    void testWriteChannel() {
        List<Double> data = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);
        String name = "X";
        processor.writeChannel(name, data);
        List<String> lines = Arrays.asList("X, 1.0, 2.0, 3.0, 4.0, 5.0");
        List<Double> result = processor.readChannel(name, lines);
        assertEquals(data, result);
    }

    @Test
    void testReadParameters() {
        List<String> paramLines = Arrays.asList("M,2.0", "C,1.0");
        Parameter expected = new Parameter();
        expected.setM(2.0);
        expected.setC(1.0);
        Parameter result = processor.readParameters(paramLines);
        assertEquals(expected.getM(), result.getM());
        assertEquals(expected.getC(), result.getC());
    }

    @Test
    void testReadParameters_Switched() {
        List<String> paramLines = Arrays.asList("C,2.0", "M,1.0");
        Parameter expected = new Parameter();
        expected.setM(1.0);
        expected.setC(2.0);
        Parameter result = processor.readParameters(paramLines);
        assertEquals(expected.getM(), result.getM());
        assertEquals(expected.getC(), result.getC());
    }

    @Test
    void testGetChannelDataForC_WithEmptyInput() {
        List<Double> X = Arrays.asList();
        channelInput.getChannel().setX(X);
        double b = 3.0;
        parameter.setB(b);
        List<Double> expected = Arrays.asList();
        Outputs result = processor.getChannelDataForC(parameter, channelInput);
        assertEquals(expected, result.getChannel().getC());
    }

    @Test
    void testGetChannelDataForB_WithEmptyInput() {
        assertThrows(IllegalArgumentException.class,
                () -> processor.getChannelDataForBandMetric_b(parameter, channelInput));
    }

    @Test
    void testGetChannelDataForA_WithEmptyInput() {
        channelInput.getChannel().setX(Arrays.asList());
        parameter.setM(2.0);
        parameter.setC(1.0);
        List<Double> expected = Arrays.asList();
        Outputs result = processor.getChannelDataForA(parameter, channelInput);
        assertEquals(expected, result.getChannel().getA());
    }
}