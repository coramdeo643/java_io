package _server_socket.ch03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerFile03 {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Scanner scanner = new Scanner(System.in);
        try {
            serverSocket = new ServerSocket(5000);
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected");
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("Client connected");
            String msg;
            System.out.println("Client connected");
            while ((msg = reader.readLine()) != null) {
                System.out.println("Message : " + msg);
            }


//            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
//            String response;
//            response = scanner.nextLine();
//            writer.write(response);
//            writer.flush();

            //while (true) {
//                if ("exit".equalsIgnoreCase(msg)) {
//                    break;
//                }
//                if(msg==null) {
//                    break;
//                }
            //}
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
