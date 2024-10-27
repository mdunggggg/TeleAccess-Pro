package com.example.teleaccesspro.client;

public class TeleClient {
    private String host;
    private int port;
    private ClientConnection clientConnection;

    public TeleClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public boolean connectToServer(String password) {
        try {
            clientConnection = new ClientConnection(host, port);
            return clientConnection.connectToServer(password);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ClientConnection getClientConnection() {
        return clientConnection;
    }

    public void setClientConnection(ClientConnection clientConnection) {
        this.clientConnection = clientConnection;
    }
}
