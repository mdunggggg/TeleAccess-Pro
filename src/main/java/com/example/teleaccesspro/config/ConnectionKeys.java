package com.example.teleaccesspro.config;

public class ConnectionKeys {
    public static String RMI_EVENT_SERVER = "rmi://192.168.1.13/event";
    public static String SAVED_DIR = "D:\\test_ltm";
    public static String PASSWORD_FILE = "src/main/java/com/example/teleaccesspro/server/password.txt";

    public static int RMI_PORT = 1099;
    public static int FILE_SERVER_PORT = 2507;
    public static int SERVER_IMAGE_PORT = 1004;

    public static final long CHUNK_THRESHOLD = 100 * 1024 * 1024; // 100MB
    public static final int CHUNK_SIZE = 10 * 1024 * 1024; // 10MB
    public static final int DEFAULT_BUFFER_SIZE = 1024 * 1024; // 1MB

    public  static final int PORT_SEND_CLIENT_SERVER = 50005;
    public  static final int PORT_SEND_SERVER_CLIENT = 50006;
    public  static final int PORT_RECEIVE_CLIENT_SERVER = 50006;
    public  static final int PORT_RECEIVE_SERVER_CLIENT = 50005;
}
