package com.example.teleaccesspro.event;

public class IDeviceEventImpl implements IDeviceEvent {
    @Override
    public void mousePressed(int button) {
        System.out.println("Mouse pressed: " + button);
    }

    @Override
    public void mouseReleased(int button) {
        System.out.println("Mouse released: " + button);
    }

    @Override
    public void keyPressed(int keyCode) {
        System.out.println("Key pressed: " + keyCode);
    }

    @Override
    public void keyReleased(int keyCode) {
        System.out.println("Key released: " + keyCode);
    }

    @Override
    public void mouseMoved(double x, double y) {
        System.out.println("Mouse moved: " + x + " " + y);
    }
}
