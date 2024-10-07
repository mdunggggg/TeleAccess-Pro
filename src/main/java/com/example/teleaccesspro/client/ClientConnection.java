package com.example.teleaccesspro.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientConnection {
    private String host;
    private int port;
    private DataInputStream dis;
    private DataOutputStream dos;
    private Socket socket;

    public ClientConnection(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        socket = new Socket(host, port);
        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());
    }

    public boolean connectToServer(String password) throws IOException {
        dos.writeUTF(password);
        dos.flush();
        String response = dis.readUTF();
        if (response.equals("success")) {
            System.out.println("Connected to server successfully!");
            return true;
        } else {
            System.out.println("Failed to connect to server!");
            return false;
        }
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

    public DataInputStream getDis() {
        return dis;
    }

    public void setDis(DataInputStream dis) {
        this.dis = dis;
    }

    public DataOutputStream getDos() {
        return dos;
    }

    public void setDos(DataOutputStream dos) {
        this.dos = dos;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
