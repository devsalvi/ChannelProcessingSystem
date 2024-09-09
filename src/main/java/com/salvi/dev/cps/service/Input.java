package com.salvi.dev.cps.service;

public class Input {
    Channel channel;
    Metric metric;

    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }

    public Input() {
        channel = new Channel();
        metric = new Metric();
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
