package _server_socket.ch04;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class MultiThreadServer2 {
    public static void main(String[] args) {
        System.out.println(" - - - Server - - - ");
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        String sourceFP = null;
        String destinFP = "b.txt";
        try {
            serverSocket = new ServerSocket(5000);
            clientSocket = serverSocket.accept();
            System.out.println(" = = = = Client connected = = = = ");

            BufferedReader keyboardReader =
                    new BufferedReader(new InputStreamReader(System.in));
            PrintWriter writerStream = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader readerStream =
                    new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            Thread readThread = new Thread(() -> {
                String clientMsg;
                try {
                    while ((clientMsg = readerStream.readLine()) != null) {
                        if ("exit".equalsIgnoreCase(clientMsg)) {
                            System.out.println("Client had exited the chatting");
                            break;
                        }
                        System.out.println("Client : " + clientMsg);
                        if ("send".equals(clientMsg)) {
                            byte[] bytes = new byte[1024];
                            int data;
                            while ((data = readerStream.read()) != -1) {
                                writerStream.write(Arrays.toString(bytes), 0, data);
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error : Message reading");
                }
            });
            Thread keyboardThread = new Thread(() -> {
                try {
                    String serverKeyboardMsg;
                    while ((serverKeyboardMsg = keyboardReader.readLine()) != null) {
                        System.out.print("Me : ");
                        writerStream.println(serverKeyboardMsg); // write() + \n
                        writerStream.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error : Message sending");
                }
            });
            keyboardThread.start();
            readThread.start();
            readThread.join(); // readThread 종료시까지 메인쓰레드 종료 금지
            keyboardThread.join(); // keyboardThread 종료시까지 메인쓰레드 종료 금지
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            e.getMessage();
            System.out.println("Error : Port is already using");
        } finally {
            try {
                if (clientSocket != null) clientSocket.close();
                if (clientSocket != null) serverSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
                e.getMessage();
                System.out.println("Error : Closing the resources");
            }
        }
    }
}
