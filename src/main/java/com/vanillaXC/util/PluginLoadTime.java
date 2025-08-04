package com.vanillaXC.util;

public class PluginLoadTime {
    private final long startTime;

    public PluginLoadTime() {
        this.startTime = System.currentTimeMillis();
    }

    public double getLoadTimeSeconds() {
        return (System.currentTimeMillis() - startTime) / 1000.0;
    }

    public String getFormattedLoadTime() {
        return String.format("%.3f", getLoadTimeSeconds());
    }
}