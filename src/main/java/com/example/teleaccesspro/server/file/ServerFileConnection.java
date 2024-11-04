package com.example.teleaccesspro.server.file;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerFileConnection extends Thread{
    ServerSocket serverSocket;
    public ServerFileConnection(int port) {
        try {
            System.out.println("Server File Transfer is running on port " + port);
            serverSocket = new ServerSocket(port);
        }
        catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            System.out.println("Awaiting file connection from client");
            Socket socket = null;
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("File connection error: " + e);
            }
            System.out.println("Connection file established");
            try {
                assert socket != null;
                new ReceiveFileHandler(socket.getInputStream()).start();
            } catch (IOException e) {
                System.out.println("Error when receiving file: " + e);
            }
        }
    }
}
