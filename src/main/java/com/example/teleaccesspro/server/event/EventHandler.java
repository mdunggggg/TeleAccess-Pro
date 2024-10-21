package com.example.teleaccesspro.server.event;

import com.example.teleaccesspro.event.IDeviceEvent;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class EventHandler extends UnicastRemoteObject implements IDeviceEvent, Runnable {
    private Robot robot;
    private double width;
    private double height;

    public EventHandler( Robot robot, String width, String height) throws RemoteException{
        this.robot = robot;
        this.width = Double.parseDouble(width.trim());
        this.height = Double.parseDouble(height.trim());
        System.out.println("Width: " + this.width + ", Height: " + this.height);
    }

    @Override
    public void run() {
        try {
            LocateRegistry.createRegistry(1099);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        try {
            Naming.bind("rmi://192.168.1.2/event", this);
            System.out.println("RMI server is runing on port 1099");
        }
        catch (MalformedURLException | AlreadyBoundException | RemoteException e) {
            System.err.println("RMI server is not running");
        }
    }

    @Override
    public void mousePressed(int button) {
        robot.mousePress(button);
    }

    @Override
    public void mouseReleased(int button) {
        robot.mouseRelease(button);
    }

    @Override
    public void keyPressed(int keyCode) {
        robot.keyPress(keyCode);
    }

    @Override
    public void keyReleased(int keyCode) {
        robot.keyRelease(keyCode);
    }

    @Override
    public void mouseMoved(double x, double y) {
        int xPosition = (int) (x * width);
        int yPosition = (int) (y * height);
        robot.mouseMove(xPosition, yPosition);
        System.out.println("Mouse moved to: " + xPosition + ", " + yPosition);
    }
}
