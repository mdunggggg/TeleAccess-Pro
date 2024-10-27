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

    Boolean authenticate() {
        try {
            String password = dis.readUTF().trim();
            if(BCrypt.checkpw(password, passwordServer)) {
                System.out.println("Correct password");
                dos.writeUTF("success");
                dos.flush();
                // Send screen width and height
                dos.writeUTF(width);
                dos.flush();
                dos.writeUTF(height);
                dos.flush();
                return true;

            } else {
                System.out.println("Incorrect password");
                dos.writeUTF("fail");
                dos.flush();
                return false;
            }
        }
        catch (Exception e) {
            System.out.println("Error: " + e);
            return false;
        }
    }
}
