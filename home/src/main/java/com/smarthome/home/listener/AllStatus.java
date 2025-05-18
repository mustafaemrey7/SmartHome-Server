package com.smarthome.home.listener;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class AllStatus {
    private HashMap<Long, Boolean> ledStatus;
    private HashMap<Long, Long> servoAngles;
    private Double temperature;
    private Double humidity;
    private String motion;

    public AllStatus() {
    }

    public AllStatus(HashMap<Long, Boolean> ledStatus,
                     HashMap<Long, Long> servoAngles,
                     Double temperature,
                     Double humidity,
                     String motion) {
        this.ledStatus = ledStatus;
        this.servoAngles = servoAngles;
        this.temperature = temperature;
        this.humidity = humidity;
        this.motion = motion;
    }
}
