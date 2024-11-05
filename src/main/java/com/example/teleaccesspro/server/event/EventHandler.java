package com.example.teleaccesspro.server.event;

import com.example.teleaccesspro.config.ConnectionKeys;
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
            LocateRegistry.createRegistry(ConnectionKeys.RMI_PORT);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        try {
            Naming.bind(ConnectionKeys.RMI_EVENT_SERVER, this);
            System.out.println("RMI server is runing on port " + ConnectionKeys.RMI_PORT);
        }
        catch (MalformedURLException | AlreadyBoundException | RemoteException e) {
            System.out.println(e);
            System.err.println("RMI server is not running");
        }
    }

    @Override
    public void mousePressed(int button) {
        System.out.println("Mouse pressed");
        System.out.println("Button: " + button);
        robot.mousePress(button);
    }

    @Override
    public void mouseReleased(int button) {
        System.out.println("Mouse released");
        System.out.println("Button: " + button);
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

    @Override
    public void mouseDragged(double x, double y) throws RemoteException {
        int xPosition = (int) (x * width);
        int yPosition = (int) (y * height);
        robot.mouseMove(xPosition, yPosition);
        System.out.println("Mouse dragged to: " + xPosition + ", " + yPosition);
    }

    @Override
    public void mouseScrolled(int scrollAmount) throws RemoteException {
        robot.mouseWheel(scrollAmount);
    }
}
