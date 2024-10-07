package com.example.teleaccesspro.client;

import javafx.application.Application;
import javafx.stage.Stage;

public class ClientUI extends Application {

    private Stage primaryStage;
    private ClientScene_EnterIPServer clientSceneEnterIPServer;
    private ClientScene_EnterPasswordServer clientSceneEnterPassWordServer;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // Khởi tạo các scene
        clientSceneEnterIPServer = new ClientScene_EnterIPServer(this);
        clientSceneEnterPassWordServer = new ClientScene_EnterPasswordServer(this);

        // Đặt scene đầu tiên là clientSceneEnterIPServer
        primaryStage.setTitle("Remote Desktop Control - Client");
        primaryStage.setScene(clientSceneEnterIPServer.getScene());
        primaryStage.show();
    }

    // Phương thức chuyển sang clientSceneEnterPassWordServer
    public void showScene2(String serverIP, int port) {
        clientSceneEnterPassWordServer.setServerIP(serverIP, port);
        primaryStage.setScene(clientSceneEnterPassWordServer.getScene());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
