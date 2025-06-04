package _server_socket.ch05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class MultiNServer2 {
    private static int count = 0;
    private static final int PORT = 5000;
    private static Vector<PrintWriter> clientWriters = new Vector<>();

    public static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }
        @Override
        public void run(){
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                clientWriters.add(out);
                String msg;
                while ((msg = in.readLine()) != null) {
                    System.out.println(msg);
                    for (PrintWriter writer : clientWriters) {
                        writer.println(msg);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace(); System.out.println(e.getMessage());
            } finally {
                try {
                    if(socket != null) socket.close();
                    clientWriters.remove(out);
                } catch (IOException e) {
                    e.printStackTrace(); System.out.println(e.getMessage());
                }
            }
        }
    } // handler

    public static void main(String[] args) {
        System.out.println("server");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                new ClientHandler(serverSocket.accept()).start();
                count++;
                System.out.println("count = " + count);
            }
        } catch (Exception e) {
            e.printStackTrace(); System.out.println(e.getMessage());
        }

    } // main
}
//