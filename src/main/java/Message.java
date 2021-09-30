package main.java;

import java.io.Serializable;

public class Message implements Serializable {
    private Long serverTimestampT2;
    private Long clientTimestampT1;
    private Long serverTimestampT3;
    private Long clientTimestampT4;

    public Long getServerTimestampT2() {
        return serverTimestampT2;
    }

    public void setServerTimestampT2(Long serverTimestampT2) {
        this.serverTimestampT2 = serverTimestampT2;
    }

    public Long getClientTimestampT1() {
        return clientTimestampT1;
    }

    public void setClientTimestampT1(Long clientTimestampT1) {
        this.clientTimestampT1 = clientTimestampT1;
    }

    public Long getServerTimestampT3() {
        return serverTimestampT3;
    }

    public void setServerTimestampT3(Long serverTimestampT3) {
        this.serverTimestampT3 = serverTimestampT3;
    }

    public Long getClientTimestampT4() {
        return clientTimestampT4;
    }

    public void setClientTimestampT4(Long clientTimestampT4) {
        this.clientTimestampT4 = clientTimestampT4;
    }

    @Override
    public String toString() {
        return "Message{" +
                "serverTimestampT2=" + serverTimestampT2 +
                ", clientTimestampT1=" + clientTimestampT1 +
                ", serverTimestampT3=" + serverTimestampT3 +
                ", clientTimestampT4=" + clientTimestampT4 +
                '}';
    }
}
