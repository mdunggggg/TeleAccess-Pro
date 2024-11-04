package com.example.teleaccesspro.voice_server;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static com.example.teleaccesspro.config.ConnectionKeys.BUFFER_SIZE;

public class VoiceSendHandler extends Thread {
    private Socket clientSocket;
    private TargetDataLine microphone;
    private SourceDataLine speaker;
    private boolean running = true;

    public VoiceSendHandler(Socket clientSocket) {
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
        DataLine.Info micInfo = new DataLine.Info(TargetDataLine.class, format);
        microphone = (TargetDataLine) AudioSystem.getLine(micInfo);
        microphone.open(format);
        microphone.start();

        // Setup speakers for receiving audio
        DataLine.Info speakerInfo = new DataLine.Info(SourceDataLine.class, format);
        speaker = (SourceDataLine) AudioSystem.getLine(speakerInfo);
        speaker.open(format);
        speaker.start();
    }

    private void handleAudioData() throws IOException {
        byte[] inputBuffer = new byte[BUFFER_SIZE];
        byte[] outputBuffer = new byte[BUFFER_SIZE];
        int bytesReadFromMicrophone;
        int bytesReadFromClient;

        try (OutputStream clientOutput = clientSocket.getOutputStream();
             InputStream clientInput = clientSocket.getInputStream()) {
            do {
                // Read from microphone and send to client
                bytesReadFromMicrophone = microphone.read(outputBuffer, 0, BUFFER_SIZE);
                if (bytesReadFromMicrophone > 0) {
                    clientOutput.write(outputBuffer, 0, bytesReadFromMicrophone);
                    System.out.println("Sent data to client: " + bytesReadFromMicrophone + " bytes");
                }

                // Read from client and play to speaker
                bytesReadFromClient = clientInput.read(inputBuffer, 0, BUFFER_SIZE);
                if (bytesReadFromClient > 0) {
                    speaker.write(inputBuffer, 0, bytesReadFromClient);
                    System.out.println("Received data from client: " + bytesReadFromClient + " bytes");
                }

                // Exit if no data in both directions
            } while (bytesReadFromMicrophone != -1 || bytesReadFromClient != -1);
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
