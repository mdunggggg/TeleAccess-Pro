package com.example.teleaccesspro.voice_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class VoiceChatServer extends Thread {
    private int port;
    private String host;
    private Socket clientSocket; // Socket for client connection
    private ServerSocket serverSocket; // Server socket for accepting client connections
    private VoiceSendHandler voiceHandler;

    public VoiceChatServer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        super.run();
        startServer();
    }

    private void startServer() {
        try {
            serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept();
            voiceHandler = new VoiceSendHandler(clientSocket);
            voiceHandler.start();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error starting server voice chat: " + e);
        }
    }
}
