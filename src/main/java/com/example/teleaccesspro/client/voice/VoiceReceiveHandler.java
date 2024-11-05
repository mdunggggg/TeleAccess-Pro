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

        byte[] bufferForInput = new byte[1024];
        int bufferVariableForInput = 0;
        OutputStream out = clientSocket.getOutputStream();
        InputStream in = clientSocket.getInputStream();

        byte[] bufferForOutput = new byte[1024];
        int bufferVariableForOutput = 0;
        while ((bufferVariableForInput = in.read(bufferForInput)) > 0 || (bufferVariableForOutput = microphone.read(bufferForOutput, 0, 1024)) > 0) {
            out.write(bufferForOutput, 0, bufferVariableForOutput);
            speaker.write(bufferForInput, 0, bufferVariableForInput);

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
