import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;


public class NTPServer {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        final int SIZE = 1024;
        DatagramSocket serverSocket = new DatagramSocket(9876);

        while (true) {
            byte[] receiveData = new byte[SIZE];
            DatagramPacket receivePacket = new DatagramPacket(receiveData,
                    receiveData.length);
            serverSocket.receive(receivePacket);
            System.out.println("Package received from client");
            new Thread(new Responder(serverSocket, receivePacket)).start();
        }
    }
}

