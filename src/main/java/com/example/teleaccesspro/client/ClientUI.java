package com.example.teleaccesspro.client;

import com.example.teleaccesspro.client.event.EventHandler;
import com.example.teleaccesspro.client.file.ClientFileConnection;
import com.example.teleaccesspro.client.file.SendFileHandler;
import com.example.teleaccesspro.config.ConnectionKeys;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.List;

public class ClientUI extends Application {

    private Stage primaryStage;
    private ClientScene_EnterIPServer clientSceneEnterIPServer;
    private ClientScene_EnterPasswordServer clientSceneEnterPassWordServer;
    private ClientScreenHandler serverScene;
    private Scene clientScene;
    private SendFileHandler sendFileHandler;
    private String serverIP;

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
        this.serverIP = serverIP;
        clientSceneEnterPassWordServer.setServerIP(serverIP, port);
        primaryStage.setScene(clientSceneEnterPassWordServer.getScene());
    }

    public void showServerScreen(ClientConnection clientConnection) throws IOException {
       // showFileChooserStage();
        ImageView imageView = new ImageView();
        imageView.setFitWidth(Double.parseDouble(clientConnection.getWidth()));
        imageView.setFitHeight(Double.parseDouble(clientConnection.getHeight()));
        imageView.setPreserveRatio(true);
        StackPane root = new StackPane(imageView);
        clientScene = new Scene(root, imageView.getFitWidth(), imageView.getFitHeight());
        InputStream inputStream = clientConnection.getSocket().getInputStream();
        serverScene = new ClientScreenHandler(
                inputStream, imageView, imageView.getFitWidth(), imageView.getFitHeight()
        );
        serverScene.start();
        new EventHandler(imageView, root, Double.parseDouble(clientConnection.getHeight()), Double.parseDouble(clientConnection.getWidth()));

        primaryStage.setScene(clientScene);
        primaryStage.show();
    }

    public void setUpDragAndDrop() throws IOException {

        clientScene.setOnDragOver(event -> {
            Dragboard db = event.getDragboard();
            if (db.hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY);
            }
            event.consume();
        });
        clientScene.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                List<File> files = db.getFiles();
                File draggedFile = files.getFirst();
                System.out.println("Dragged file: " + draggedFile.getAbsolutePath());
                sendFile(draggedFile);
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }

    public void showFileChooserStage() {
        Stage fileChooserStage = new Stage();
        fileChooserStage.setTitle("Choose or Drag a File");
        Button openFileButton = new Button("Open File");
        openFileButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All Files", "*.*"),
                    new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
            );

            File selectedFile = fileChooser.showOpenDialog(fileChooserStage);
            if (selectedFile != null) {
                System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                sendFile(selectedFile);
            }
        });

        VBox root = new VBox(10, openFileButton);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 300, 200);

        fileChooserStage.setScene(scene);
        fileChooserStage.show();
    }

    void sendFile(File file) {
        ClientFileConnection clientFileConnection = null;
        try {
            clientFileConnection = new ClientFileConnection(serverIP, ConnectionKeys.FILE_SERVER_PORT);
        } catch (IOException e) {
            System.out.println("Error connecting to file server: " + e.getMessage());
        }
        Socket socket = clientFileConnection.getFileSocket();
        try {
            sendFileHandler = new SendFileHandler(socket.getOutputStream(), file);
        } catch (IOException e) {
            System.out.println("Error creating SendFileHandler: " + e.getMessage());
        }
        sendFileHandler.start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
