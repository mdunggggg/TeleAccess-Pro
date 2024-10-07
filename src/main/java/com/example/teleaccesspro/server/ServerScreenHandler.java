package com.example.teleaccesspro.server;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class ServerScreenHandler extends Thread{

    private OutputStream oos;
    private Robot robot;
    private Rectangle rectangle;

    public ServerScreenHandler(OutputStream oos, Robot robot, Rectangle rectangle) {
        this.oos = oos;
        this.robot = robot;
        this.rectangle = rectangle;
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            BufferedImage image = robot.createScreenCapture(rectangle);
            System.out.println("Sending screen to client " + image);
            try {
                ImageIO.write(image, "jpeg", oos);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
