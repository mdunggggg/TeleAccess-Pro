package com.example.teleaccesspro.client;

import com.example.teleaccesspro.client.voice.VoiceChatClient;
import com.example.teleaccesspro.server.voice.VoiceChatServer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ClientScene_EnterPasswordServer {
    private Scene scene;
    private ClientUI clientUI;
    private String serverIP;
    private int port;
    private ClientConnection clientConnection;
    private ImageView imageView;

    public ClientScene_EnterPasswordServer(ClientUI clientUI) {
        this.clientUI = clientUI;
        createScene();
    }

    // Đặt địa chỉ IP từ scene1
    public void setServerIP(String serverIP, int port) {
        this.serverIP = serverIP;
        this.port = port;
    }

    private void createScene() {
        Label label = new Label("Nhập mật khẩu của Server:");
        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("Mật khẩu");
        Button submitButton = new Button("Submit");

        submitButton.setOnAction(e -> {
            String password = passwordInput.getText().trim();
            if (!password.isEmpty()) {
                // Lấy giá trị người dùng nhập vào
                System.out.println("Địa chỉ IP Server: " + serverIP);
                System.out.println("Mật khẩu: " + password);
                // Kết nối đến server
                try {
                    connectToServer(serverIP, port, password);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi Nhập Liệu");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng nhập mật khẩu.");
                alert.showAndWait();
            }
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(label, passwordInput, submitButton);

        scene = new Scene(layout, 400, 200);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
    }

    private void connectToServer(String serverIP, int port, String password) throws IOException {
        clientConnection = new ClientConnection(serverIP, port);
        if (clientConnection.connectToServer(password)) {
            clientUI.showServerScreen(clientConnection);
            clientUI.setUpDragAndDrop();
            VoiceChatClient voiceChatClient = new VoiceChatClient(serverIP, 50005);
            voiceChatClient.start();

            VoiceChatServer voiceChatServer = new VoiceChatServer(serverIP, 50006);
            voiceChatServer.start();
        }

    }

    public Scene getScene() {
        return scene;
    }


}
