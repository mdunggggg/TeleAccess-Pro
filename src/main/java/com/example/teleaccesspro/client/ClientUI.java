package com.example.teleaccesspro.client;

import com.example.teleaccesspro.client.event.EventHandler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import javafx.scene.layout.StackPane;

public class ClientUI extends Application {

    private Stage primaryStage;
    private ClientScene_EnterIPServer clientSceneEnterIPServer;
    private ClientScene_EnterPasswordServer clientSceneEnterPassWordServer;
    private ClientScreenHandler serverScene;

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

    public void showServerScreen(ClientConnection clientConnection) throws IOException {
        ImageView imageView = new ImageView();
        imageView.setFitWidth(Double.parseDouble(clientConnection.getWidth()));
        imageView.setFitHeight(Double.parseDouble(clientConnection.getHeight()));
        imageView.setPreserveRatio(true);
        StackPane root = new StackPane(imageView);
        Scene scene = new Scene(root, imageView.getFitWidth(), imageView.getFitHeight());
        InputStream inputStream = clientConnection.getSocket().getInputStream();
        ClientScreenHandler clientScreenHandler = new ClientScreenHandler(
                inputStream, imageView, imageView.getFitWidth(), imageView.getFitHeight()
        );
        clientScreenHandler.start();
        new EventHandler(imageView, root, Double.parseDouble(clientConnection.getHeight()), Double.parseDouble(clientConnection.getWidth()));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
