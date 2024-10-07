package com.example.teleaccesspro.server;

import org.mindrot.jbcrypt.BCrypt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerConnectionHandler{
    Socket socket;
    DataOutputStream dos;
    DataInputStream dis;
    String passwordServer;
    String width;
    String height;

    public ServerConnectionHandler(Socket socket, String passwordServer, String width, String height) throws IOException {
        this.socket = socket;
        this.dos = new DataOutputStream(socket.getOutputStream());
        this.dis = new DataInputStream(socket.getInputStream());
        this.passwordServer = passwordServer;
        this.width = width;
        this.height = height;
    }

    void authenticate() {
        try {
            String password = dis.readUTF().trim();
            if(BCrypt.checkpw(password, passwordServer)) {
                System.out.println("Correct password");
                dos.writeUTF("success");

            } else {
                System.out.println("Incorrect password");
                dos.writeUTF("fail");
            }
        }
        catch (Exception e) {

        }
    }
}
