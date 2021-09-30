package main.java;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Responder implements Runnable {

    DatagramSocket socket = null;
    DatagramPacket datagramPacket = null;

    public Responder(DatagramSocket socket, DatagramPacket datagramPacket) {
        this.socket = socket;
        this.datagramPacket = datagramPacket;
    }

    public void run() {
        Long receiveTimestamp = Utility.getServerTime();
        InetAddress IPAddress = datagramPacket.getAddress();
        int port = datagramPacket.getPort();
        Message message = Utility.deserializeToString(datagramPacket.getData());
        message.setServerTimestampT2(receiveTimestamp);
        message.setServerTimestampT3(Utility.getServerTime());
        ByteArrayOutputStream bStream = Utility.serializeToByteArray(message);
        byte[] serializedMessage = bStream.toByteArray();
        DatagramPacket sendPacket = new DatagramPacket(serializedMessage,
                bStream.size(), IPAddress, port);
        try {
            socket.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
