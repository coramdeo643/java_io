package _client_socket.ch05;

import java.io.*;
import java.net.Socket;

/**
 * 서버와 양방향 통신하는 클라이언트 측 코드
 */
public class MultiNClient {
    // Member variables
    private final String name;
    private Socket socket;
    private PrintWriter writerStream; // 서버로 메시지 전송 스트림
    private BufferedReader readerStream;
    private BufferedReader keyboardReader;

    // Constructor
    public MultiNClient(String name) {
        this.name = name;
    }

    // Method - Connect to port 5000 Server
    private void connectToServer() throws IOException {
        socket = new Socket("192.168.0.132", 5000);
        System.out.println(" = connected to the server");
    }

    // Method - Set up the Input/Output Stream
    private void setupStream() throws IOException {
        writerStream = new PrintWriter(socket.getOutputStream(), true);
        readerStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        keyboardReader = new BufferedReader(new InputStreamReader(System.in));
    }

    // Method - Read the message from the Server
    private Thread createdReadThread() {
        return new Thread(() -> {
            try {
                String serverMsg;
                while ((serverMsg = readerStream.readLine()) != null) {
                    System.out.println(serverMsg);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        });
    }

    // Method - Read the message from the keyboard
    private Thread createdWriteThread() {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String keyboardMsg;
                    while ((keyboardMsg = keyboardReader.readLine()) != null) {
                        writerStream.println("[" + name + "] : " + keyboardMsg);
                        // writerStream.flush();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
        });
    }

    // Method - Close the resources and the socket
    private void cleanup() {
        try {
            if(socket != null) {
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    // Method - Read + write thread 생성하여 양방향 통신 시작
    private void startCommunication() throws InterruptedException {
        Thread readThread = createdReadThread();
        Thread writeThread = createdWriteThread();

        readThread.start();
        writeThread.start();

        readThread.join();
        writeThread.join();
    }

    // chatStart() Method
    public void chatRun() {
        try {
            connectToServer();
            setupStream();
            startCommunication();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            cleanup();
        }
    }

    // main
    public static void main(String[] args) {
        System.out.println("Client program start");
        MultiNClient nClient = new MultiNClient("스윙");
        nClient.chatRun();
    }
} // class
