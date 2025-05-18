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


    @PostMapping("/led/{roomId}/{state}")
    public void updateLed(@PathVariable Long roomId, @PathVariable String state) throws MqttException {
        mqttService.sendLedCommand(roomId,state);
    }

    @PostMapping("/ac/{roomId}/{state}")
    public void updateAc(@PathVariable Long roomId, @PathVariable String state) throws MqttException {
        mqttService.sendAcCommand(roomId,state);
    }

    @PostMapping("/lock/{roomId}/{state}")
    public void updateLock(@PathVariable Long roomId, @PathVariable String state) throws MqttException {
        mqttService.sendLockCommand(roomId,state);
    }

    @PostMapping("/camera/{roomId}/{command}")
    public void updateCamera(@PathVariable Long roomId, @PathVariable String command) throws MqttException {
        mqttService.sendServoCommand(roomId,command);
    }

    @GetMapping("/getHomeStatus")
    public HomeStatus getHomeStatus() {
        return homeStatusService.getCurrentStatus();
    }

    @GetMapping("/getAllStatus")
    public AllStatus getAllStatus(){
        return mqttService.getAllStatus();

    }
}
