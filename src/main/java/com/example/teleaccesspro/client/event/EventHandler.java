package com.example.teleaccesspro.client.event;

import com.example.teleaccesspro.config.ConnectionKeys;
import com.example.teleaccesspro.event.IDeviceEvent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.awt.event.InputEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class EventHandler {
    private ImageView imageView;
    private double height;
    private double width;
    private IDeviceEvent deviceEvent;
    private Pane root;

    public EventHandler(ImageView imageView, Pane root, double height, double width) throws IOException {
        this.imageView = imageView;
        this.height = height;
        this.width = width;
        setUpRmi();
        setEventHandlers(root);
    }

    private void setUpRmi(){
        try{
            deviceEvent = (IDeviceEvent)Naming.lookup(ConnectionKeys.RMI_EVENT_SERVER);
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

    private void setEventHandlers(Pane pane) {
        imageView.setOnMousePressed(event -> {
            System.out.println("Mouse pressed from imageView");
            try {
                deviceEvent.mousePressed(InputEvent.BUTTON1_DOWN_MASK);
            } catch (RemoteException e) {
                System.err.println("Remote exception in mousePressed");
            }
        });

        imageView.setOnMouseReleased(event -> {
            try {
                deviceEvent.mouseReleased(InputEvent.BUTTON1_DOWN_MASK);
            } catch (RemoteException e) {
                System.err.println("Remote exception in mouseReleased");
            }
        });


        imageView.setOnMouseMoved(event -> {
            try {
                deviceEvent.mouseMoved((event.getSceneX() / width), ((event.getSceneY() / height)));
            } catch (RemoteException e) {
               System.err.println("Remote exception in mouseMoved");
            }
        });

        imageView.setOnScroll(event -> {
            try {
                deviceEvent.mouseScrolled((int) event.getDeltaY());
            } catch (RemoteException e) {
                System.err.println("Remote exception in mouseScrolled");
            }
        });

        imageView.setOnMouseDragged(event -> {
            try {
                deviceEvent.mouseDragged((event.getSceneX() / width), ((event.getSceneY() / height)));
            } catch (RemoteException e) {
                System.err.println("Remote exception in mouseDragged");
            }
        });


        pane.setOnKeyPressed(event -> {
            System.out.println("Key pressed from pane");
            System.out.println(event.getCode());
            try {
                deviceEvent.keyPressed(event.getCode().getCode());
            } catch (RemoteException e) {
                System.out.println("Remote exception in keyPressed");
            }
        });

        pane.setOnKeyReleased(event -> {
            System.out.println("Key released from pane");
            System.out.println(event.getCode());
            try {
                deviceEvent.keyReleased(event.getCode().getCode());
            } catch (RemoteException e) {
                System.out.println("Remote exception in keyReleased");
            }
        });

        pane.setOnMouseClicked(event -> {
            pane.requestFocus();
        });

        pane.setFocusTraversable(true);
        pane.requestFocus();
    }


}
