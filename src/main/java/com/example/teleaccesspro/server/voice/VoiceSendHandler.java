package com.example.teleaccesspro.server.voice;

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
        byte[] bufferForInput = new byte[1024];
        int bufferVariableForInput = 0;
        byte[] bufferForOutput = new byte[1024];
        int bufferVariableForOutput = 0;
        OutputStream out = null;
        out = clientSocket.getOutputStream();
        InputStream in = clientSocket.getInputStream();
        while(((bufferVariableForOutput = microphone.read(bufferForOutput, 0, 1024)) > 0) || (bufferVariableForInput = in.read(bufferForInput)) > 0) {
            out.write(bufferForOutput, 0, bufferVariableForOutput);
            speaker.write(bufferForInput, 0, bufferVariableForInput);
            System.out.println("Voice sent/recieved");
            System.out.println(bufferForInput);
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
