package _server_socket.ch04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
            serverSocket.accept();
            System.out.println(" = = = = Client connected = = = = ");
            // What we need :
            // 1. InputStream from my keyboard
            // 2. OutputStream connected to Client(Sending my data)
            // 3. InputStream connected to Client(Getting your data)

            // 1. Byte, Letter-based + SubStream
            // System.in + InputStreamReader + BufferedReader
            BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in));
            // 2. clientSocket get outputStream
            // Byte + Letter-based + sub(not yet)
            PrintWriter writerStream = new PrintWriter(clientSocket.getOutputStream());


            // 1.1. What to do? repeat! while!
            // 메인 작업자가 계속 키보드 입력을 받아서 코드로 가져오는 것은 너무 바쁨
            // 람다 표현식
            Thread keyboardThread = new Thread(() -> {
                try {
                    String serverKeyboardMsg;
                    while ((serverKeyboardMsg = keyboardReader.readLine()) != null) {
                        writerStream.println(serverKeyboardMsg); // write() + \n
                    }
                } catch (IOException e) {
                    System.out.println("Error : Message sending");
                }
            });

            // 계속 while 돌면서 키보드에서 값을 읽고 소켓과 연결된 스트림을 통해서
            // 데이터를 보내는 작업을 한다
            // keyboardThread.start();

        } catch (IOException e) {
            e.printStackTrace(); e.getMessage();
            System.out.println("Error : Port is already using");
        } finally {
            try {
                if (clientSocket != null) clientSocket.close();
                if (clientSocket != null) serverSocket.close();
            } catch (Exception e) {
                e.printStackTrace(); e.getMessage();
                System.out.println("Error : Closing the resources");
            }
        }
    }
}
