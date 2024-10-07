package com.example.teleaccesspro.server;


import java.awt.*;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnection {

    static String host = "localhost";

    ServerSocket serverSocket;

    public ServerConnection(int port) {
        try {
            System.out.println("Server is running on port " + port);
            serverSocket = new ServerSocket(port);

            GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gDev = gEnv.getDefaultScreenDevice();

            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            String width = "" + dim.getWidth();
            String height = "" + dim.getHeight();
            Rectangle rectangle = new Rectangle(dim);
            Robot robot = new Robot(gDev);

            while (true) {
                System.out.println("Awaiting connection from client");
                Socket socket = serverSocket.accept();
                DataInputStream dis = new DataInputStream(new FileInputStream("src/main/java/com/example/teleaccesspro/server/password.txt"));
                String password = dis.readUTF().trim();
                System.out.println("Awaiting connection from client");
                new ServerConnectionHandler(socket, password).start();
            }

        }
        catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }


}
