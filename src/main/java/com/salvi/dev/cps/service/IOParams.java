package com.salvi.dev.cps.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Input class to store the values of A, B, C, X and Y
 */
public class IOParams {
    Channel channel;
    Metric metric;

    public IOParams() {
        channel = new Channel();
        metric = new Metric();
    }

    public Channel getChannel() {
        return channel;
    }

    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }

    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
