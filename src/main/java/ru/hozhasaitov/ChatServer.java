package ru.hozhasaitov;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer implements Runnable {
    private final ArrayList<Client> clients = new ArrayList<>();
    private final ServerSocket server;

    public ChatServer() throws IOException {
        server = new ServerSocket(1234);

    }

    @Override
    public  void  run(){
        while (true) {
            System.out.println("Waiting...");
            try {
                // ждем клиента из сети
                Socket socket = server.accept();
                System.out.println("Client connected!");
                // создаем клиента на своей стороне
                clients.add(new Client(socket, this));
                // запускаем поток

            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public  void sendAll(String message, Client sender) {
        clients.stream()
                .filter(c -> !c.equals(sender))
                .forEach(c -> c.receive(message));
    }

}
