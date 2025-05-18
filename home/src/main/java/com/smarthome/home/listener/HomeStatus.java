package com.smarthome.home.listener;

public class HomeStatus {
    private double temperature;
    private double humidity;

    public HomeStatus(double temperature, double humidity) {
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }
}
