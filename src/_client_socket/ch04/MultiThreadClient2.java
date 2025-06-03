package _client_socket.ch04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MultiThreadClient2 {
    public static void main(String[] args) {
        System.out.println("= = = = Client = = = =");
        Socket socket = null;
        try {
            socket = new Socket("localhost", 5000);
            System.out.println("> > > > Connected to Server < < < <");
            PrintWriter writerStream = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader readerStream =
                    new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in));
            Thread readThread = new Thread(() -> {
                try {
                    String serverMsg;
                    while ((serverMsg = readerStream.readLine()) != null) {
                        if ("exit".equalsIgnoreCase(serverMsg)) {
                            System.out.println("Server had exited the chatting");
                            break;
                        }
                        System.out.println("Server : " + serverMsg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error : Msg reading ");
                }
            });
            Thread writeThread = new Thread(() -> {
                try {
                    String clientMsg;
                    while ((clientMsg = keyboardReader.readLine()) != null) {
                        writerStream.println("Me : "+clientMsg);
                        writerStream.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error : Msg sending");
                }
            });
            readThread.start();
            writeThread.start();
            readThread.join();
            writeThread.join();
        } catch (Exception e) {
            e.printStackTrace(); e.getMessage();
            System.out.println("Error : ");
        } finally {
            try{
                if (socket != null) socket.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error : Closing the resources");
            }
        }
    } // end of main
} // end of class
