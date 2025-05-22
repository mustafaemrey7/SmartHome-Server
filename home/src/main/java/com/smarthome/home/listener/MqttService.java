package com.smarthome.home.listener;

import jakarta.annotation.PostConstruct;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MqttService {

    private MqttClient client;

    private HashMap<Long,Boolean> ledStatus = new HashMap<>();
    private HashMap<Long,Long> servoAngles = new HashMap<>();

    private String temperature = "55.0";
    private String humidity = "52.2";
    private String motion = "HIGH";

    private final Integer acId= 4;
    private final Integer lockId= 5;
    @PostConstruct
    public void init() {

        for (long i = 0; i <= 5; i++) {
            ledStatus.put(i, i % 2 != 0);
        }

        for (long i = 0; i <= 3; i++) {
            servoAngles.put(i, i*15);
        }

        try {
            client = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
            client.connect();

            client.subscribe("led/query", this::handleMessage);
            client.subscribe("servo/query", this::handleMessage);
            client.subscribe("dht/temp", this::handleMessage);
            client.subscribe("dht/hum", this::handleMessage);
            client.subscribe("motion", this::handleMessage);

            System.out.println("MQTT connected and subscribed.");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void handleMessage(String topic, MqttMessage message) {
        String msg = new String(message.getPayload());
        switch (topic) {
            case "led/query" -> updateLedStatus(msg);
            case "servo/query" -> updateServoAngles(msg);
            case "dht/temp" -> temperature = msg;
            case "dht/hum" -> humidity = msg;
            case "motion" -> motion = msg;
        }
    }

    private void updateLedStatus(String msg) {
        List<String> ledStatusOnOff = List.of(msg.split(":"));
        String ledId = ledStatusOnOff.get(0);
        String ledState = ledStatusOnOff.get(1);
        updateLedStatusMap(Long.valueOf(ledId),ledState.equals("HIGH"));
    }

    private void updateLedStatusMap(Long ledId, Boolean ledState) {
        for (Map.Entry<Long, Boolean> entry : ledStatus.entrySet()) {
            Long key = entry.getKey();
            if (key.equals(ledId)){
                ledStatus.put(ledId, ledState);
            }
        }
    }

    private void updateServoAngles(String msg) {
        List<String> ledServoStatusAngles = List.of(msg.split(":"));
        String servoId = ledServoStatusAngles.get(0);
        String servoAngle = ledServoStatusAngles.get(1);
        updateServoAnglesMap(Long.valueOf(servoId),Long.valueOf(servoAngle));
    }

    private void updateServoAnglesMap(Long servoId, Long servoAngle) {
        for (Map.Entry<Long, Long> entry : servoAngles.entrySet()) {
            Long key = entry.getKey();
            if (key.equals(servoId)){
                servoAngles.put(servoId, servoAngle);
            }
        }
    }

    public void sendLedCommand(Long roomId, String state) throws MqttException {
        String message = roomId + ":" + state;
        MqttMessage mqttMessage = new MqttMessage(message.getBytes());
        client.publish("led/command", mqttMessage);
    }

    public void sendAcCommand(String state) throws MqttException {/*4*/
        String message = acId + ":" + state;
        MqttMessage mqttMessage = new MqttMessage(message.getBytes());
        client.publish("led/command", mqttMessage);
    }

    public void sendLockCommand(String state) throws MqttException {/*5*/
        String message = lockId + ":" + state;
        MqttMessage mqttMessage = new MqttMessage(message.getBytes());
        client.publish("led/command", mqttMessage);
    }

    public void sendServoCommand(Long roomId, String command) throws MqttException {
        String message = roomId + ":" + command;
        MqttMessage mqttMessage = new MqttMessage(message.getBytes());
        client.publish("servo/command", mqttMessage);
    }


    public AllStatus getAllStatus() {
        List<Led> ledList = ledStatus.entrySet().stream()
                .map(entry -> new Led(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        List<Servo> servoList = servoAngles.entrySet().stream()
                .map(entry -> new Servo(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        return new AllStatus(
                ledList,
                servoList,
                Double.valueOf(temperature),
                Double.valueOf(humidity),
                motion
        );
    }

    public void allLigtsOn() {
        try {
            //String message = 0 + ":on" ;
            //MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            client.publish("led/command",  new MqttMessage("0:on".getBytes()));
            client.publish("led/command",  new MqttMessage("1:on".getBytes()));
            client.publish("led/command",  new MqttMessage("2:on".getBytes()));
            client.publish("led/command",  new MqttMessage("3:on".getBytes()));
        }
        catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void allLigtsOff() {
        try {
            client.publish("led/command",  new MqttMessage("0:off".getBytes()));
            client.publish("led/command",  new MqttMessage("1:off".getBytes()));
            client.publish("led/command",  new MqttMessage("2:off".getBytes()));
            client.publish("led/command",  new MqttMessage("3:off".getBytes()));
        }
        catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
