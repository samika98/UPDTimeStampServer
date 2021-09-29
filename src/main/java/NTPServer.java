
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;


public class NTPServer {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        final int SIZE = 1024;
        DatagramSocket serverSocket = new DatagramSocket(9876);

        while (true) {

            byte[] receiveData = new byte[SIZE];
            DatagramPacket receivePacket = new DatagramPacket(receiveData,
                    receiveData.length);
            serverSocket.receive(receivePacket);
            Long receiveTimestamp = Utility.getServerTime();
            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
            Message message = Utility.deserializeToString(receivePacket.getData());
            message.setServerTimestampT2(receiveTimestamp);
            message.setServerTimestampT3(Utility.getServerTime());
            ByteArrayOutputStream bStream = Utility.serializeToByteArray(message);
            byte[] serializedMessage = bStream.toByteArray();
            DatagramPacket sendPacket = new DatagramPacket(serializedMessage,
                    bStream.size(), IPAddress, port);
            serverSocket.send(sendPacket);
        }

    }

//    public void startServer() throws SocketException {
//        DatagramSocket serverSocket = new DatagramSocket();
//        while(true){
//            serverSocket.receive();
//        }
//    }



    }

