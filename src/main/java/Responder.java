import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Responder implements Runnable {

    DatagramSocket socket = null;
    DatagramPacket datagramPacket = null;

    /*
    @param1 : socket - connectionless server socket for the time stamp server
    @param2 : datagramPacket : received packet from the client
     */
    public Responder(DatagramSocket socket, DatagramPacket datagramPacket) {
        this.socket = socket;
        this.datagramPacket = datagramPacket;
    }

    /*
    Running multiple threads to perform the parsing and de-parsing logic in parallel
    for multiple client requests.
     */
    public void run() {
        System.out.println("Package response building");
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
            System.out.println("Sending package response");
            socket.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
