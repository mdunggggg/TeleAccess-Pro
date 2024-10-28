package com.example.teleaccesspro.server.file;

import com.example.teleaccesspro.config.ConnectionKeys;

import java.io.*;
import java.net.Socket;

public class ReceiveFileHandler extends Thread {
    private final InputStream inputStream;
    private final String saveDirectory; // Thư mục lưu file

    public ReceiveFileHandler( InputStream inputStream) {
        this.inputStream = inputStream;
        this.saveDirectory = ConnectionKeys.SAVED_DIR;
    }

    @Override
    public void run() {
        super.run();
        try {
            receiveFile();
        }
        catch (IOException e) {
            System.out.println("Error when receive file: " + e);
        }
    }

    private void receiveFile() throws IOException {
        StringBuilder fileNameBuilder = new StringBuilder();
        int charRead;

        // Đọc tên file cho đến khi gặp dòng mới
        while ((charRead = inputStream.read()) != -1) {
            if (charRead == '\n') {
                break; // Kết thúc tên file
            }
            fileNameBuilder.append((char) charRead);
        }

        String fileName = fileNameBuilder.toString();
        long fileSize = Long.parseLong(readLine(inputStream)); // Đọc kích thước file từ luồng

        // Tạo đường dẫn hoàn chỉnh cho file lưu
        File file = new File(saveDirectory, fileName); // Sử dụng thư mục saveDirectory
        file.getParentFile().mkdirs(); // Tạo thư mục nếu chưa tồn tại
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            byte[] buffer = new byte[4096]; // Bộ đệm để đọc dữ liệu
            int bytesRead;
            long totalBytesRead = 0;

            // Đọc dữ liệu từ socket và ghi vào file
            while (totalBytesRead < fileSize && (bytesRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;
            }

            System.out.println("File received and saved at: " + file.getAbsolutePath());
        }
    }

    // Phương thức hỗ trợ để đọc một dòng từ InputStream
    private String readLine(InputStream inputStream) throws IOException {
        StringBuilder line = new StringBuilder();
        int charRead;
        while ((charRead = inputStream.read()) != -1) {
            if (charRead == '\n') {
                break; // Kết thúc dòng
            }
            line.append((char) charRead);
        }
        return line.toString();
    }
}
