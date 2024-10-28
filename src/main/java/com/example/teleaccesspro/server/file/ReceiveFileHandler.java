package com.example.teleaccesspro.server.file;

import com.example.teleaccesspro.config.ConnectionKeys;

import java.io.*;
import java.net.Socket;
import java.util.zip.GZIPInputStream;

public class ReceiveFileHandler extends Thread {
    private final InputStream inputStream;
    private final String saveDirectory; // Thư mục lưu file
    private static final long CHUNK_THRESHOLD = 100 * 1024 * 1024; // 100MB
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

        String fileName = readLine(inputStream);
        // Đọc kích thước file
        long fileSize = Long.parseLong(readLine(inputStream));

        // Xác định file lưu
        File file = new File(saveDirectory, fileName);
        file.getParentFile().mkdirs();

        // Kiểm tra và xử lý theo kích thước file
        if (fileSize > CHUNK_THRESHOLD) {
            receiveFileInChunks(file, fileSize);
        } else {
            receiveCompressedFile(file);
        }

        System.out.println("File received and saved at: " + file.getAbsolutePath());
    }

    private void receiveFileInChunks(File file, long fileSize) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            long totalBytesRead = 0;

            // Đọc và ghi từng chunk vào file
            while (totalBytesRead < fileSize && (bytesRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;
            }
            System.out.println("Chunked file received.");
        }
    }

    // Đọc file khi file nén (file <= 100MB)
    private void receiveCompressedFile(File file) throws IOException {
        try (GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
             FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
//            while ((bytesRead = gzipInputStream.read(buffer)) != -1) {
//                fileOutputStream.write(buffer, 0, bytesRead);
//            }
            System.out.println("Compressed file received and decompressed.");
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
