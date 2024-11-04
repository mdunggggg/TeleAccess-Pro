package com.example.teleaccesspro.client.voice;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class VoiceReceiveHandler extends Thread {
    private Socket clientSocket;
    private TargetDataLine microphone;
    private SourceDataLine speaker;

    public VoiceReceiveHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            setupAudioLines();
            handleAudioData();
        } catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
        } finally {
            cleanup();
        }
    }

    private void setupAudioLines() throws LineUnavailableException {
        // Audio format setup
        AudioFormat format = new AudioFormat(16000, 8, 2, true, true);

        // Setup microphone for sending audio
        DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
        speaker = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
        speaker.open(format);
        speaker.start();

        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        microphone = (TargetDataLine) AudioSystem.getLine(info);
        microphone.open(format);
        microphone.start();

    }

    private void handleAudioData() throws IOException {
        try (
                OutputStream outputStream = clientSocket.getOutputStream();
                InputStream inputStream = clientSocket.getInputStream();
        ) {
            byte[] outputBuffer = new byte[1024];
            byte[] inputBuffer = new byte[1024];
            int bytesReadFromMic = 0, bytesReadFromStream = 0;
            while ((bytesReadFromStream = inputStream.read(inputBuffer)) > 0 ||
                    (bytesReadFromMic = microphone.read(outputBuffer, 0, outputBuffer.length)) > 0) {
                if (bytesReadFromMic > 0) {
                    outputStream.write(outputBuffer, 0, bytesReadFromMic);
                }
                if (bytesReadFromStream > 0) {
                    speaker.write(inputBuffer, 0, bytesReadFromStream);
                }
            }
        }
    }

    private void cleanup() {
        if (microphone != null) {
            microphone.stop();
            microphone.close();
        }
        if (speaker != null) {
            speaker.stop();
            speaker.close();
        }
        System.out.println("Audio handling stopped.");
    }
}
