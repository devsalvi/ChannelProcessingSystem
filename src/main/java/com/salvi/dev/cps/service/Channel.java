package com.salvi.dev.cps.service;

import java.util.List;

/*
 * Channel class to store the values of A, B, C, X and Y
 */
public class Channel {

    List<Double> A;
    List<Double> B;
    List<Double> C;
    List<Double> X;
    List<Double> Y;

    public List<Double> getA() {
        return A;
    }

    public void setA(List<Double> a) {
        A = a;
    }

    public List<Double> getB() {
        return B;
    }

    public void setB(List<Double> b) {
        B = b;
    }

    public List<Double> getC() {
        return C;
    }

    public void setC(List<Double> c) {
        C = c;
    }

    public List<Double> getX() {
        return X;
    }

    public void setX(List<Double> x) {
        X = x;
    }

    public List<Double> getY() {
        return Y;
    }

    public void setY(List<Double> y) {
        Y = y;
    }
}
