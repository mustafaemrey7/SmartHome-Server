package com.smarthome.home.listener;

public class Servo {
    private Long id;
    private Long angle;

    public Servo() {}

    public Servo(Long id, Long angle) {
        this.id = id;
        this.angle = angle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAngle() {
        return angle;
    }

    public void setAngle(Long angle) {
        this.angle = angle;
    }
}
