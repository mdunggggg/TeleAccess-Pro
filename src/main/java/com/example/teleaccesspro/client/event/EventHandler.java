package com.example.teleaccesspro.client.event;

import com.example.teleaccesspro.event.DeviceEvent;
import com.example.teleaccesspro.event.IDeviceEvent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class EventHandler {
    private Socket socket;
    private ImageView pane;
    private PrintWriter printWriter;
    private double height;
    private double width;
    private IDeviceEvent deviceEvent;

    public EventHandler(Socket socket, ImageView pane, double height, double width) throws IOException {
        this.socket = socket;
        this.pane = pane;
        this.printWriter = new PrintWriter(socket.getOutputStream());
        this.height = height;
        this.width = width;

        setUpRmi();
        setEventHandlers();
    }

    private void setUpRmi(){
        try{
            deviceEvent = (IDeviceEvent)Naming.lookup("rmi://localhost/event");
        }
        catch (NotBoundException e) {
            System.err.println("RMI client is not running");
        }
        catch (MalformedURLException e) {
            System.err.println("Malformed URL");
        }
        catch (RemoteException e) {
            System.err.println("Remote exception");
        }
    }

    private void setEventHandlers() {
//        pane.setOnMousePressed(event -> {
//            printWriter.println("PRESS_MOUSE");
//            printWriter.println(event.getSceneX() / width);
//            printWriter.println(event.getSceneY() / height);
//            printWriter.flush();
//        });
//
//        pane.setOnMouseReleased(event -> {
//            printWriter.println("RELEASE_MOUSE");
//            printWriter.println(event.getSceneX() / width);
//            printWriter.println(event.getSceneY() / height);
//            printWriter.flush();
//        });

        pane.setOnMouseMoved(event -> {
            try {
                deviceEvent.mouseMoved((event.getSceneX() / width), ((event.getSceneY() / height)));
            } catch (RemoteException e) {
               System.err.println("Remote exception in mouseMoved");
            }
        });

//        pane.setOnKeyPressed(event -> {
//            printWriter.println("PRESS_KEY");
//            printWriter.println(event.getCode().toString());
//            printWriter.flush();
//        });
//
//        pane.setOnKeyReleased(event -> {
//            printWriter.println("RELEASE_KEY");
//            printWriter.println(event.getCode().toString());
//            printWriter.flush();
//        });
    }


}