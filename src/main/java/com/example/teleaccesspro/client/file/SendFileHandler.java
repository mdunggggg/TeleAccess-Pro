package com.example.teleaccesspro.client.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

public class SendFileHandler {
    private OutputStream os;

    public SendFileHandler(OutputStream os) {
        this.os = os;
    }

    public void sendFile(File file) {
        try{
            FileInputStream fileInputStream = new FileInputStream(file);
            os.write((file.getName() + "\n").getBytes());
            long fileSize = file.length();
            os.write((fileSize + "\n").getBytes());
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.flush(); //
            System.out.println("File sent successfully");
        }

        catch (Exception e) {
            System.out.println("Error when send file: " + e);
        }
    }

    public OutputStream getOs() {
        return os;
    }

    public void setOs(OutputStream os) {
        this.os = os;
    }
}
