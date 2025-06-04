package _server_socket.ch05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/**
 * 1:N 양방향 통신을 구현하는 Server code
 * 여러 clients와 연결하여 메세지 수신,
 * 모든 클라이언트에게 브로드캐스트 처리한다
 */
public class MultiNServer {
    private static int CONNECTED_COUNT = 0;
    private static final int PORT = 5000; // port number setting
    // List 계열 자료구조
    private static Vector<PrintWriter> clientWriters = new Vector<>();
    // data structure needed

    /**
     * 각 클라이언트와 통신을 처리하는 Thread Inner Class
     */
    public static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                // 1. 자료구조에 서버-클라이언트 연결된 출력 스트림 저장
                // 2. 클라이언트 측과 연결된 출력스트림을 자료구조에 저장

                clientWriters.add(out);
                // 클라이언트로부터 메시지 수신하고 방송할 예정
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Received" + message);
                    for (PrintWriter writer : clientWriters) {
                        writer.println("방송 : " + message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error : " + e.getMessage());
            } finally {
                try {
                    if (socket != null) socket.close();
                    // static 자료 구조
                    clientWriters.remove(out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    } // inner class

    /**
     * 클라이언트와 통신 처리
     * 메시지 수신, 모든 클라이언트에게 broadcast 하고
     * 연결종료시 자원 정리
     */

    // test code
    public static void main(String[] args) {
        System.out.println("server");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            // MainThread Unlimited loop waiting client connection
            while (true) {
                new ClientHandler(serverSocket.accept()).start();
                CONNECTED_COUNT++;
                System.out.println("New client connected : " + CONNECTED_COUNT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //multiNServer.accept;
    } // main
} // outer class
