package com.smarthome.home.listener;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class DeviceController {

    private final MqttService mqttService;

    public DeviceController(MqttService mqttService) {
        this.mqttService = mqttService;
    }

    @PostMapping("/led/{state}")
    public void setLed(@PathVariable String state) throws MqttException {
        mqttService.sendLedCommand(state);
    }

    @PostMapping("/servo/{dir}")
    public void setServo(@PathVariable String dir) throws MqttException {
        mqttService.sendServoCommand(dir);
    }

    @GetMapping("/status")
    public DeviceStatus getStatus() {
        return mqttService.getDeviceStatus();
    }
}
