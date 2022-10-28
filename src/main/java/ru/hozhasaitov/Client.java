package ru.hozhasaitov;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Scanner;

public class Client implements Runnable {
    private final Socket socket;
    private Scanner in;
    private PrintStream out;
    private final ChatServer server;

    public Client(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
        new Thread(this).start();
    }

    public void receive(String message) {
        out.println(message);
    }
    @Override
    public void run() {
        try {
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            in = new Scanner(is);
            out = new PrintStream(os);

            out.println("Welcome to chat!");
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            out.println("Enter your message");
            String input = in.nextLine();
            while (!input.equals("bye")) {
                server.sendAll(this + " " + time + ": " + input, this);
                in.reset();
                input = in.nextLine();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (!Objects.equals(socket, client.socket)) return false;
        if (!Objects.equals(in, client.in)) return false;
        if (!Objects.equals(out, client.out)) return false;
        return Objects.equals(server, client.server);
    }

    @Override
    public int hashCode() {
        int result = in != null ? in.hashCode() : 0;
        result = 31 * result + (out != null ? out.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Client " + Thread.currentThread().getName();
    }
}
