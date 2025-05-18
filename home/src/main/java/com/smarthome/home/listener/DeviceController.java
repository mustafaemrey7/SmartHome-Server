package com.smarthome.home.listener;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class DeviceController {

    private final MqttService mqttService;

    private final HomeStatusService homeStatusService;

    public DeviceController(MqttService mqttService, HomeStatusService homeStatusService) {
        this.mqttService = mqttService;
        this.homeStatusService = homeStatusService;
    }

    @PostMapping("/led/{state}")
    public void setLed(@PathVariable String state) throws MqttException {
        mqttService.sendLedCommand(state);
    }

    @PostMapping("/ac/{state}")
    public void setAc(@PathVariable String state) throws MqttException {
        mqttService.sendAcCommand(state);
    }

    @PostMapping("/servo/{dir}")
    public void setServo(@PathVariable String dir) throws MqttException {
        mqttService.sendServoCommand(dir);
    }

    @GetMapping("/getHomeStatus")
    public HomeStatus getHomeStatus() {
        return homeStatusService.getCurrentStatus();
    }
}
