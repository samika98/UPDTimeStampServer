import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Client1 {

    public static void main(String args[]) throws Exception {
        while(true) {
            int SIZE = 1024;
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(
                    System.in));

            DatagramSocket clientSocket = new DatagramSocket();
            System.out.println("Client port" + clientSocket.getLocalAddress() + " dsf" + clientSocket.getInetAddress() + " " + clientSocket.getPort());

            InetAddress IPAddress = InetAddress.getByName("localhost");

            byte[] receiveData = new byte[SIZE];

            Message request = new Message();
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
            Message message = Utility.deserializeToString(receivePacket.getData());
            message.setClientTimestampT4(t4);

        }
    }
}
