package com.example.teleaccesspro.client;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ClientScene_EnterIPServer {
    private Scene scene;
    private ClientUI clientUI;

    public ClientScene_EnterIPServer(ClientUI clientUI) {
        this.clientUI = clientUI;
        createScene();
    }

    private void createScene() {
        Label label = new Label("Nhập địa chỉ IP của Server:");
        TextField ipInput = new TextField();
        ipInput.setPromptText("Địa chỉ IP");
        Button okButton = new Button("OK");

        okButton.setOnAction(e -> {
            String serverIP = ipInput.getText().trim();
            if (!serverIP.isEmpty()) {
                clientUI.showScene2(serverIP, 1004);
            } else {
                // Hiển thị thông báo lỗi hoặc yêu cầu nhập lại
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi Nhập Liệu");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng nhập địa chỉ IP.");
                alert.showAndWait();
            }
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(label, ipInput, okButton);

        scene = new Scene(layout, 400, 200);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
    }

    public Scene getScene() {
        return scene;
    }
}

