package com.example.teleaccesspro.event;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IDeviceEvent extends Remote {
    public void mousePressed(int button) throws RemoteException;
    public void mouseReleased(int button) throws RemoteException;
    public void keyPressed(int keyCode) throws RemoteException;
    public void keyReleased(int keyCode) throws RemoteException;
    public void mouseMoved(double x, double y) throws RemoteException;
    public void mouseDragged(double x, double y) throws RemoteException;
    public void mouseScrolled(int scrollAmount) throws RemoteException;
}
