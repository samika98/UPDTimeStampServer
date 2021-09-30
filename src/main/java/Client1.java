package main.java;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client1 {

    public static void main(String args[]) throws Exception {
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(
                System.in));

        DatagramSocket clientSocket = new DatagramSocket();
        System.out.println("Client port" + clientSocket.getLocalAddress() + " dsf" + clientSocket.getInetAddress() + " " + clientSocket.getPort());

        InetAddress IPAddress = InetAddress.getByName("localhost");

        MessageDetails messageDetails = ClientService.getClientOffset(IPAddress, clientSocket);

        System.out.println("LOCAL TIME :" + messageDetails.getMessage().getClientTimestampT4());
        System.out.println("SERVER TIME :" + messageDetails.getMessage().getServerTimestampT3());
        System.out.println("RTT_ESTIMATE : " + messageDetails.getRtt());


    }
}
