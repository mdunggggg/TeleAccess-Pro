package com.example.teleaccesspro.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TeleServer {
    private String host;
    private int port;
    private ServerSocket serverSocket;

    public TeleServer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public boolean startServer(String password) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket, password);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void stopServer() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
                System.out.println("Server stopped.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private String password;

        public ClientHandler(Socket clientSocket, String password) {
            this.clientSocket = clientSocket;
            this.password = password;
        }

        @Override
        public void run() {
            try {
                // Code to handle client connection and password verification here
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                // Implement password verification logic here
                // If password is correct, proceed with connection handling

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        TeleServer server = new TeleServer("localhost", 2208);
        server.startServer("your_password_here");
    }
}

