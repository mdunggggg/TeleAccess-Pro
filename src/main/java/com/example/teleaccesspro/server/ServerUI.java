package com.example.teleaccesspro.server;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.mindrot.jbcrypt.BCrypt;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ServerUI extends Application {


    @Override
    public void start(Stage primaryStage) {
        // Tạo tiêu đề và các thành phần của giao diện
        Label passwordLabel = new Label("Enter Password:");
        PasswordField passwordField = new PasswordField();
        Button saveButton = new Button("Save Password");

        // Áp dụng CSS từ file external
        passwordLabel.getStyleClass().add("label");
        passwordField.getStyleClass().add("password-field");
        saveButton.getStyleClass().add("save-button");

        // Tạo Label để hiển thị thông báo nổi
        Label toastLabel = new Label();
        toastLabel.getStyleClass().add("toast-label");
        toastLabel.setVisible(false); // Ẩn thông báo ban đầu

        // Xử lý sự kiện khi bấm nút lưu
        saveButton.setOnAction(e -> {
            String password = passwordField.getText();
            try {
                savePassword(password);
                showToast(toastLabel, "Password saved successfully!", primaryStage);
                Thread serverThread = new Thread(() -> {
                    new ServerConnection(1004);
                });
                serverThread.setDaemon(true);
                serverThread.start();

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Bố cục giao diện
        VBox root = new VBox(10, passwordLabel, passwordField, saveButton);
        root.setPadding(new Insets(20));
        root.getStyleClass().add("root");

        // Sử dụng StackPane để thông báo xuất hiện chồng lên giao diện chính
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(root, toastLabel);

        Scene scene = new Scene(stackPane, 300, 150);
        scene.getStylesheets().add(getClass().getResource("server-ui.css").toExternalForm());

        primaryStage.setTitle("Server UI");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void savePassword(String password) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream("src/main/java/com/example/teleaccesspro/server/password.txt"));
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));
        dataOutputStream.writeUTF(hashed);
        System.out.println("Password saved to file: " + password);
    }

    // Phương thức hiển thị thông báo nổi
    private void showToast(Label toastLabel, String message, Stage primaryStage) {
        toastLabel.setText(message);
        toastLabel.setVisible(true);

        // Tạo hiệu ứng để ẩn thông báo sau 3 giây
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(3),
                ae ->  {
                    toastLabel.setVisible(false);
                }

        ));
        timeline.play();
    }

    public static void main(String[] args) {
        removeOldPasswordFile();
        launch(args);
    }

    private static void removeOldPasswordFile() {
        File file = new File("password.txt");
        if (file.exists()) {
            file.delete();
        }
    }
}
