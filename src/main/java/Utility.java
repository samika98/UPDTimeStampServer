import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.Date;

public class Utility {

    public static Object getObjectFromJson(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, Message.class);
    }

    /*
    @returns : the local system time
     */
    public static Long getServerTime() {
        Date serverDate = new Date();
        return serverDate.getTime();
    }

    /*
    @param : message packet, to be serialized
    @return : ByteArray - to be sent on the UDP channel
    This function takes in the message dataType and converts it to a byte array
    that can be sent over the channel.
     */
    public static ByteArrayOutputStream serializeToByteArray(Message message) {
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        ObjectOutput oo = null;
        try {
            oo = new ObjectOutputStream(bStream);
            oo.writeObject(message);
            oo.close();
        } catch (IOException e) {
            System.out.println("Exception");
        }
        return bStream;

    }


    /*
    @param : byte[] buffer : byte array recieved from the channel
    @return : Message : returns the message data type
    This function takes in the byte array and deserializes it to
    form a understandable Message class object
     */
    public static Message deserializeToString(byte[] buffer) {
        try {
            ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(buffer));
            Message message = (Message) iStream.readObject();
            iStream.close();
            return message;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return new Message();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return new Message();
        }
    }
}
