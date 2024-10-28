package com.example.teleaccesspro.client.file;

import java.io.IOException;
import java.net.Socket;

public class ClientFileConnection {
    private Socket fileSocket;
    private String host;
    private int port;

    public ClientFileConnection(String host, int port) throws IOException {
       try {
           this.host = host;
           this.port = port;
           fileSocket = new Socket(host, port);
           System.out.println("Connected to server for file transfer");
       }
       catch (Exception e) {
           System.out.println("Error: " + e);
       }
    }

    public Socket getFileSocket() {
        return fileSocket;
    }

    public void setFileSocket(Socket fileSocket) {
        this.fileSocket = fileSocket;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
