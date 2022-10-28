package ru.hozhasaitov;

import java.io.IOException;

public class StartServer {
    public static void main(String[] args) {
        try {
            Thread chatServer = new Thread(new ChatServer());
            chatServer.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
