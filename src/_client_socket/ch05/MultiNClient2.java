package _client_socket.ch05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MultiNClient2 {
    private final int PORT = 5000;
    private final String name;
    private Socket socket;
    private PrintWriter wStream;
    private BufferedReader rStream;
    private BufferedReader kbReader;

    public MultiNClient2(String name) {
        this.name = name;
    }

    private void connect() throws IOException {
        socket = new Socket("localhost", PORT);
        System.out.println("server");
    }

    private void setup() throws IOException {
        wStream = new PrintWriter(socket.getOutputStream(), true);
        rStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        kbReader = new BufferedReader(new InputStreamReader(System.in));
    }

    private Thread createRThread() {
        return new Thread(() -> {
            String serverMsg;
            try {
                while ((serverMsg = rStream.readLine()) != null) {
                    System.out.println(serverMsg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private Thread createWThread() {
        return new Thread(() -> {
            String kbMsg;
            try {
                while ((kbMsg = kbReader.readLine())!= null) {
                    wStream.println(kbMsg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void cleanup() {
        try {
            if(socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startComm() throws InterruptedException {
        Thread rThread = createRThread();
        Thread wThread = createWThread();
        rThread.start();
        wThread.start();
        rThread.join();
        wThread.join();
    }

    public void chatRun() {
        try {
            connect();
            setup();
            startComm();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            cleanup();
        }
    }

    public static void main(String[] args) {
        System.out.println("client");
        MultiNClient2 nClient2 = new MultiNClient2("");
        nClient2.chatRun();
    }
}
