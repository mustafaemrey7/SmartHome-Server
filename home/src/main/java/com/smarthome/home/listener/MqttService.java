package com.smarthome.home.listener;

import jakarta.annotation.PostConstruct;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.stereotype.Service;

@Service
public class MqttService {

    private MqttClient client;

    private String ledStatus = "unknown";
    private String servoAngle = "unknown";
    private String temperature = "";
    private String humidity = "";
    private String motion = "";

    @PostConstruct
    public void init() {
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
            case "led/query" -> ledStatus = msg;
            case "servo/query" -> servoAngle = msg;
            case "dht/temp" -> temperature = msg;
            case "dht/hum" -> humidity = msg;
            case "motion" -> motion = msg;
        }
    }

    public void sendLedCommand(String state) throws MqttException {
        client.publish("led/command", new MqttMessage(state.getBytes()));
    }

    public void sendServoCommand(String dir) throws MqttException {
        client.publish("servo/command", new MqttMessage(dir.getBytes()));
    }

    public DeviceStatus getDeviceStatus() {
        return new DeviceStatus(ledStatus, servoAngle, temperature, humidity, motion);
    }
}
