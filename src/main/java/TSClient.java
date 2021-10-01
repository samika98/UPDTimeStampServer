import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class TSClient {

    public static void main(String args[]) throws Exception {

        String host_name;
        if (args.length == 0) {
            host_name = "localhost";
        } else {
            host_name = args[0];
        }

        int SIZE = 1024;

        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName(host_name);

        byte[] receiveData = new byte[SIZE];
        long rtt = 0L, sum = 0L;
        Message message = null;
        /*
        Sending 50 requests from client to form a large enough sample size to calculate ttl
         */
        for (int i = 0; i < 50; i++) {
            Message request = new Message();
            request.setClientTimestampT1(System.currentTimeMillis());

            ByteArrayOutputStream bStream = Utility.serializeToByteArray(request);
            byte[] serializedMessage = bStream.toByteArray();

            DatagramPacket sendPacket = new DatagramPacket(serializedMessage,
                    serializedMessage.length, IPAddress, 10488);

            clientSocket.send(sendPacket);

            DatagramPacket receivePacket = new DatagramPacket(receiveData,
                    receiveData.length);

            clientSocket.receive(receivePacket);
            Long t4 = Utility.getServerTime();
            message = Utility.deserializeToString(receivePacket.getData());
            message.setClientTimestampT4(t4);
            rtt = ((message.getServerTimestampT2() - message.getClientTimestampT1()) + (message.getServerTimestampT3() - message.getClientTimestampT4())) / 2;
            sum += rtt;

            /*
            Logging the output of every 5th request to prove that the server is
            handling inter-leaving requests
             */
            if (i % 5 == 0) {
                System.out.println("The" + String.valueOf(i) + "th package was received at " + message.getClientTimestampT4());
            }
        }

        double rtt_final = rtt / 50;

        System.out.println("REMOTE_TIME \t" + message.getServerTimestampT3());
        System.out.println("LOCAL_TIME \t" + message.getClientTimestampT4());
        System.out.println("RTT_ESTIMATE \t" + rtt_final);

    }
}
