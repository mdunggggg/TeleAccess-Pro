package com.example.teleaccesspro.event;

public enum DeviceEvent {
    PRESS_MOUSE(-1),
    RELEASE_MOUSE(-2),
    PRESS_KEY(-3),
    RELEASE_KEY(-4),
    MOVE_MOUSE(-5);

    private int code;
    public int getCode() {
        return code;
    }

    DeviceEvent(int code) {
        this.code = code;
    }
}
