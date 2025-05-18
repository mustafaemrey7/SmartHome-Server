package com.smarthome.home.listener;

import org.springframework.stereotype.Service;

@Service
public class HomeStatusService {

    public HomeStatus getCurrentStatus() {

        double temperature = 23.7; // örnek sabit değer
        double humidity = 55.4;

        return new HomeStatus(temperature, humidity);
    }
}
