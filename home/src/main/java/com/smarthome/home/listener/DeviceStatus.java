package com.smarthome.home.listener;

public record DeviceStatus(
        String ledStatus,
        String servoAngle,
        String temperature,
        String humidity,
        String motion
) {}

