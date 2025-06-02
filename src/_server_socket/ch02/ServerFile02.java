package _server_socket.ch02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server code
 * 양방향 통신 구현
 */
public class ServerFile02 {
    public static void main(String[] args) {
        // 1. Server socket + port number (5000)
        // 2. InputStream(to get message from client)
        // 3. OutputStream(to send message to client)

        // 1.
        ServerSocket serverSocket = null;
        try {
            // 서버 소켓에서는 나의 IP 주소 이미 알고있음
            serverSocket = new ServerSocket(5000);
            // socket created, waiting for connection
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected");
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            // Read
            String msg = reader.readLine();
            System.out.println("Message : " + msg);
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
            // clientSocket 에서 get outputStream > print writer

            // Write
            writer.println("Hi, nice to meet you client");
            writer.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    System.out.println("Error from socket closing");
                    e.printStackTrace();
                }
            }
        }


    }
}
