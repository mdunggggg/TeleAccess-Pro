package com.example.teleaccesspro.server;

import org.mindrot.jbcrypt.BCrypt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerConnectionHandler extends Thread{
    Socket socket;
    DataOutputStream dos;
    DataInputStream dis;
    String passwordServer;

    public ServerConnectionHandler(Socket socket, String passwordServer) throws IOException {
        this.socket = socket;
        this.dos = new DataOutputStream(socket.getOutputStream());
        this.dis = new DataInputStream(socket.getInputStream());
        this.passwordServer = passwordServer;
    }

    @Override
    public void run() {
        super.run();
        try {
            String password = dis.readUTF().trim();
            if(BCrypt.checkpw(password, passwordServer)) {
                System.out.println("Correct password");
            } else {
                System.out.println("Incorrect password");
            }
        }
        catch (Exception e) {

        }
    }
}
