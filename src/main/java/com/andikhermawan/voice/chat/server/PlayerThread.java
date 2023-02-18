/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.andikhermawan.voice.chat.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.SourceDataLine;

/**
 *
 * @author andik
 */
public class PlayerThread extends Thread {
    private DatagramSocket datagramSocket;
    private SourceDataLine audioOut;
    private byte[] buffers = new byte[512];

    @Override
    public void run() {
        DatagramPacket incoming = new DatagramPacket(buffers, buffers.length);
        while (VoiceChatServer.isCalling()) {            
            try {
                datagramSocket.receive(incoming);
                buffers = incoming.getData();
                audioOut.write(buffers, 0, buffers.length);
            } catch (IOException ex) {
                Logger.getLogger(PlayerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        audioOut.close();
        audioOut.drain();
        System.out.println("stop");
    }

    public DatagramSocket getDatagramSocket() {
        return datagramSocket;
    }

    public void setDatagramSocket(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
    }

    public SourceDataLine getAudioOut() {
        return audioOut;
    }

    public void setAudioOut(SourceDataLine audioOut) {
        this.audioOut = audioOut;
    }
}
