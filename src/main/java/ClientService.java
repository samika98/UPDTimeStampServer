package main.java;

import jdk.swing.interop.SwingInterOpUtils;

import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

public class ClientService {

    private static int SAMPLE_SIZE = 500;
    private static int SIZE = 1024;

    public static MessageDetails getClientOffset(InetAddress IPAddress, DatagramSocket clientSocket) throws Exception {
        byte[] receiveData = new byte[SIZE];
        Message request = new Message();
        Message message = null;
        Long rtt = 0L;
        List<Long> rttList = new ArrayList<>();
        for (int i = 0; i < SAMPLE_SIZE; i++) {
            request.setClientTimestampT1(System.currentTimeMillis());

            ByteArrayOutputStream bStream = Utility.serializeToByteArray(request);
            byte[] serializedMessage = bStream.toByteArray();

            DatagramPacket sendPacket = new DatagramPacket(serializedMessage,
                    serializedMessage.length, IPAddress, 9876);

            clientSocket.send(sendPacket);

            DatagramPacket receivePacket = new DatagramPacket(receiveData,
                    receiveData.length);

            clientSocket.receive(receivePacket);
            Long t4 = Utility.getServerTime();
            message = Utility.deserializeToString(receivePacket.getData());
            message.setClientTimestampT4(t4);
            rtt = ((message.getServerTimestampT2() - message.getClientTimestampT1()) + (message.getServerTimestampT3() - message.getClientTimestampT4())) / 2;
            rttList.add(rtt);
            System.out.println("Packet details" + message.toString() );
        }

        OptionalDouble rttFinal = rttList.stream().mapToLong(value -> value).average();

        return new MessageDetails(message, rttFinal.isPresent() ? rttFinal.getAsDouble() : 0.0);
    }

}
