package com.example.teleaccesspro.server;


import com.example.teleaccesspro.client.voice.VoiceChatClient;
import com.example.teleaccesspro.config.ConnectionKeys;
import com.example.teleaccesspro.server.event.EventHandler;
import com.example.teleaccesspro.server.file.ServerFileConnection;
import com.example.teleaccesspro.server.voice.VoiceChatServer;

import java.awt.*;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnection {

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
            (new Thread(new EventHandler(robot, width, height))).start();
            while (true) {
                System.out.println("Awaiting connection from client");
                Socket socket = serverSocket.accept();
                DataInputStream dis = new DataInputStream(new FileInputStream(ConnectionKeys.PASSWORD_FILE));
                String password = dis.readUTF().trim();
                System.out.println("Awaiting connection from client");
                Boolean authenticatedn = new ServerConnectionHandler(socket, password, width, height).authenticate();
                new ServerScreenHandler(socket.getOutputStream(), robot, rectangle).start();
                if(authenticatedn) {
                    ServerFileConnection serverFileConnection = new ServerFileConnection(ConnectionKeys.FILE_SERVER_PORT);
                    serverFileConnection.start();
                    VoiceChatServer voiceChatServer = new VoiceChatServer(ConnectionKeys.CLIENT_IP, 50005);
                    voiceChatServer.start();

                    VoiceChatClient voiceChatClient = new VoiceChatClient(ConnectionKeys.CLIENT_IP, 50006);
                    voiceChatClient.start();
                }

             }

        }
        catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }


}
