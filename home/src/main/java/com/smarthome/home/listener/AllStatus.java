package com.smarthome.home.listener;

import java.util.List;

public class AllStatus {
    private List<Led> ledStatus;
    private List<Servo> servoAngles; // Burayı List<Servo> yaptık
    private Double temperature;
    private Double humidity;
    private String motion;

    public AllStatus() {}

    public AllStatus(List<Led> ledStatus, List<Servo> servoAngles, Double temperature, Double humidity, String motion) {
        this.ledStatus = ledStatus;
        this.servoAngles = servoAngles;
        this.temperature = temperature;
        this.humidity = humidity;
        this.motion = motion;
    }

    public List<Led> getLedStatus() {
        return ledStatus;
    }

    public void setLedStatus(List<Led> ledStatus) {
        this.ledStatus = ledStatus;
    }

    public List<Servo> getServoAngles() {
        return servoAngles;
    }

    public void setServoAngles(List<Servo> servoAngles) {
        this.servoAngles = servoAngles;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public String getMotion() {
        return motion;
    }

    public void setMotion(String motion) {
        this.motion = motion;
    }
}
