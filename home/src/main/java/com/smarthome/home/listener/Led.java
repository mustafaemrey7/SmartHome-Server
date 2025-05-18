package com.smarthome.home.listener;

public class Led {
    private Long id;
    private Boolean state;

    public Led() {}

    public Led(Long id, Boolean state) {
        this.id = id;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}
