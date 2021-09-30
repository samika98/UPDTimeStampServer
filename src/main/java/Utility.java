package main.java;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.Date;

public class Utility {

    public static Object getObjectFromJson(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, Message.class);
    }

    public static Long getServerTime() {
        Date serverDate = new Date();
        return serverDate.getTime();
    }

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

    public static Message deserializeToString(byte[] buffer) {
        try {
            ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(buffer));
            Message message = (Message) iStream.readObject();
            iStream.close();
            return message;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new Message();
        }
    }
}
