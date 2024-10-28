package com.example.teleaccesspro.server.file;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerFileConnection {
    ServerSocket serverSocket;
    public ServerFileConnection(int port) {
        try {
            System.out.println("Server File Transfer is running on port " + port);
            serverSocket = new ServerSocket(port);

            while (true) {
                System.out.println("Awaiting file connection from client");
                Socket socket = serverSocket.accept();
                System.out.println("Connection file established");
                new ReceiveFileHandler(socket.getInputStream()).start();
            }
        }
        catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
