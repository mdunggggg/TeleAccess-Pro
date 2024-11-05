package com.example.teleaccesspro.client.voice;

import java.io.IOException;
import java.net.Socket;

public class VoiceChatClient extends Thread{
    private int port;
    private String host;
    private  VoiceReceiveHandler voiceHandler;
    private Socket clientSocket;

    public VoiceChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        super.run();
        startClient();
    }

    private void startClient() {
        try {
            clientSocket = new Socket(host, port);
            voiceHandler = new VoiceReceiveHandler(clientSocket);
            voiceHandler.start();
        } catch (IOException e) {
            System.out.println("Error starting client voice chat: " + e);
        }
    }
}
