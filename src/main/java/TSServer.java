import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;


public class TSServer {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        final int SIZE = 1024;
        DatagramSocket serverSocket = new DatagramSocket(10488);

        System.out.println("Server started, waiting for packages..");

        /*
        Code to start the time stamp server
         */
        while (true) {
            byte[] receiveData = new byte[SIZE];
            DatagramPacket receivePacket = new DatagramPacket(receiveData,
                    receiveData.length);
            serverSocket.receive(receivePacket);
            System.out.println("Package received from client");
            /*
            Running multiple threads to handle the business logic of the application,
            keeping the port free for accepting multiple requests.
             */
            new Thread(new Responder(serverSocket, receivePacket)).start();
        }
    }
}

