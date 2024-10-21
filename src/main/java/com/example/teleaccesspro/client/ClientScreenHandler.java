package com.example.teleaccesspro.client;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ClientScreenHandler extends Thread {
    private InputStream is;
    private ImageView imageView;
    private Double width;
    private Double height;

    public ClientScreenHandler(InputStream is, ImageView imageView, Double width, Double height) {
        this.is = is;
        this.imageView = imageView;
        this.width = width;
        this.height = height;
        Platform.runLater(this::openImageScreen);
    }

    @Override
    public void run() {
        while (true) {
            byte[] bufferImage = new byte[1024 * 1024];
            int count = 0;
            try {
                do {
                    count += is.read(bufferImage, count, bufferImage.length - count);
                } while (!(count > 4 && bufferImage[count - 2] == (byte) -1 && bufferImage[count - 1] == (byte) -39));
                ByteArrayInputStream bais = new ByteArrayInputStream(bufferImage, 0, count);
                BufferedImage bufferedImage = ImageIO.read(bais);

                if (bufferedImage != null) {
                    Image fxImage = SwingFXUtils.toFXImage(bufferedImage, null);
                    Platform.runLater(() -> imageView.setImage(fxImage)); // Update UI on JavaFX thread
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void openImageScreen() {
        Stage imageStage = new Stage();
        imageStage.setTitle("Image Screen");

        StackPane root = new StackPane(imageView);
        Scene scene = new Scene(root, width, height);

        imageStage.setScene(scene);
        imageStage.show();
    }
}
