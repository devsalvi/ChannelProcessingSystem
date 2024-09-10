package com.salvi.dev.cps.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Parameter class to store the values of m and c
 */
public class Parameter {
    double m;
    double c;

    public double getM() {
        return m;
    }

    public void setM(double m) {
        this.m = m;
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
