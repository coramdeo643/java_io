package _client_socket.ch06;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * 1:1 socket file client
 */
public class FileClient {

    private static final int PORT = 5000;
    private static final String address = "localhost"; // 192.168.0.132.
    private final String name;
    private Socket socket;
    private OutputStream out;
    private InputStream in;
    private final Scanner scanner = new Scanner(System.in);

    public FileClient(String name) {
        this.name = name;
    } // constructor

    private void connectToServer() throws IOException {
        socket = new Socket(address, PORT);
        System.out.println("Connected to the Server!");
    }

    private void setupStreams() throws IOException {
        out = socket.getOutputStream();
        in = socket.getInputStream();
        System.out.println("Ready to run and connected to the Socket");
    }

    /**
     * 키보드에서 파일 경로를 입력 받아서 서버로 파일 보내기
     * @throws IOException
     */
    private void uploadFile() throws IOException {
        System.out.println("Path?");
        String filePath = scanner.nextLine();
        // defensive code
        // 파일이 정말 있는지 확인
        File file = new File(filePath);
        if(!file.exists() && file.isFile()) {
            System.out.println("No file is here");
            return;
        }
        // code

    }











} // end of clas
