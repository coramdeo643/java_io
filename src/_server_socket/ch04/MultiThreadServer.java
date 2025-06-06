package _server_socket.ch04;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadServer {
    public static void main(String[] args) {
        System.out.println(" - - - Server executed - - - ");
        // Ingredients
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        try {
            serverSocket = new ServerSocket(5000);
            // Waiting the client connection
            clientSocket = serverSocket.accept();
            System.out.println(" = = = = Client connected = = = = ");
            // What we need :
            // 1. InputStream from my keyboard
            // 2. OutputStream connected to Client(Sending my data)
            // 3. InputStream connected to Client(Getting your data)

            // 1. Byte, Letter-based + SubStream
            // System.in + InputStreamReader + BufferedReader
            BufferedReader keyboardReader =
                    new BufferedReader(new InputStreamReader(System.in));
            // 2. get OutputStream from clientSocket
            // Byte + Letter-based + sub(not yet)
            PrintWriter writerStream = new PrintWriter(clientSocket.getOutputStream(), true);
            // 3. get InputStream from clientSocket
            // Byte + Letter-based + sub(buffer)
            BufferedReader readerStream =
                    new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            // 3.1. while - getting data from client
            // 람다
            Thread readThread = new Thread(() -> {
                String clientMsg;
                try {
                    while ((clientMsg = readerStream.readLine()) != null) {
                        if ("exit".equalsIgnoreCase(clientMsg)) {
                            System.out.println("Client had exited the chatting");
                            break;
                        }
                        System.out.println("Client : " + clientMsg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error : Message reading");
                }
            });

            // 1.1. What to do? repeat! while!
            // 메인 작업자가 계속 키보드 입력을 받아서 코드로 가져오는 것은 너무 바쁨
            // 람다 표현식
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

            // Order threads to run
            keyboardThread.start();
            readThread.start();

            // join() when threads are using
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
