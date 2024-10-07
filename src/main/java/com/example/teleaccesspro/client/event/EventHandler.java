package com.example.teleaccesspro.client.event;

import com.example.teleaccesspro.event.DeviceEvent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class EventHandler {
    private Socket socket;
    private ImageView pane;
    private PrintWriter printWriter;
    private double height;
    private double width;

    public EventHandler(Socket socket, ImageView pane, double height, double width) throws IOException {
        this.socket = socket;
        this.pane = pane;
        this.printWriter = new PrintWriter(socket.getOutputStream());
        this.height = height;
        this.width = width;

        setEventHandlers();
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
            printWriter.println(DeviceEvent.MOVE_MOUSE.getCode());
            printWriter.println((event.getSceneX() / width));
            printWriter.println(event.getSceneY() / height);
            printWriter.flush();
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
