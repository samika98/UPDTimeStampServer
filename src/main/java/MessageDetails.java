package main.java;

public class MessageDetails {
    private Message message;
    private double rtt;

    public MessageDetails(Message message, double rtt) {
        this.message = message;
        this.rtt = rtt;
    }

    public Message getMessage() {
        return message;
    }

    public double getRtt() {
        return rtt;
    }
}
