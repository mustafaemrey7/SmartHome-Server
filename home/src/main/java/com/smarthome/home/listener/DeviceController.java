package com.smarthome.home.listener;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/ac/{state}")
    public void updateAc(@PathVariable String state) throws MqttException {
        mqttService.sendAcCommand(state);
    }

    @PostMapping("/lock/{state}")
    public void updateLock( @PathVariable String state) throws MqttException {
        mqttService.sendLockCommand(state);
    }

    @PostMapping("/camera/{roomId}/{command}")
    public void updateCamera(@PathVariable Long roomId, @PathVariable String command) throws MqttException {
        mqttService.sendServoCommand(roomId,command);
    }

    @GetMapping("/getHomeStatus")
    public HomeStatus getHomeStatus() {
        return homeStatusService.getCurrentStatus();
    }

    @GetMapping(value = "/getAllStatus")
    @ResponseBody
    public ResponseEntity<AllStatus> getAllStatus() {
        return ResponseEntity.ok().body(mqttService.getAllStatus());
    }

    @PostMapping(value ="/allLigts/on" )
    public void allLigtsOn()  {
        mqttService.allLigtsOn();
    }

    @PostMapping(value ="/allLigts/off" )
    public void allLigtsOff()  {
        mqttService.allLigtsOff();
    }
}
