package com.example.teleaccesspro.client.file;

import com.example.teleaccesspro.config.ConnectionKeys;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPOutputStream;

public class SendFileHandler extends Thread {
    private OutputStream os;
    private File file;

    public SendFileHandler(OutputStream os, File file) {
        this.os = os;
        this.file = file;
    }


    @Override
    public void run() {
        super.run();
        sendFile();
    }

    public void sendFile() {
        try{
            long fileSize = file.length();
            os.write((file.getName() + "\n").getBytes());
            os.write((fileSize + "\n").getBytes());
            os.flush();

            if (fileSize > ConnectionKeys.CHUNK_THRESHOLD) {
                System.out.println("File size is greater than 100MB, sending in chunks");
                sendFileInChunks(file);
            } else {
                System.out.println("File size is less than 100MB, sending in one go");
                sendCompressedFile(file);
            }

            System.out.println("File sent successfully");
        }

        catch (Exception e) {
            System.out.println("Error when send file: " + e);
        }
    }

    private void sendCompressedFile(File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file);
             GZIPOutputStream gzipOutputStream = new GZIPOutputStream(os)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                gzipOutputStream.write(buffer, 0, bytesRead);
                System.out.println("Compressed " + bytesRead + " bytes.");
            }
            gzipOutputStream.finish();
            os.flush();
            System.out.println("Compressed file sent.");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e);
        } catch (IOException e) {
            System.out.println("Error when sending compressed file: " + e);
        }
    }

    private void sendFileInChunks(File file) throws IOException {
        long fileSize = file.length();
        int totalChunks = (int) Math.ceil((double) fileSize / ConnectionKeys.CHUNK_SIZE);

        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] buffer = new byte[ConnectionKeys.CHUNK_SIZE];
            int bytesRead;

            for (int chunkIndex = 0; chunkIndex < totalChunks; chunkIndex++) {
                bytesRead = fileInputStream.read(buffer);
                if (bytesRead == -1) break;
                os.write(buffer, 0, bytesRead);
                os.flush();
                System.out.println("Chunk " + (chunkIndex + 1) + "/" + totalChunks + " sent, " + bytesRead + " bytes.");
            }
            System.out.println("Chunked file sent.");
        }
    }

    public OutputStream getOs() {
        return os;
    }

    public void setOs(OutputStream os) {
        this.os = os;
    }
}
