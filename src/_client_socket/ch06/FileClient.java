package _client_socket.ch06;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * 1:1 socket file client
 */
// C:\employees.zip
public class FileClient {

    private static final int PORT = 5000;
    private static final String address = "192.168.0.80"; // 192.168.0.132.
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
     *
     * @throws IOException
     */
    private void uploadFile() throws IOException {
        System.out.println("File Path : ");
        String filePath = scanner.nextLine();
        // defensive code
        // 파일이 정말 있는지 확인
        File file = new File(filePath);
        if (!file.exists() && file.isFile()) {
            System.out.println("No file is here");
            return;
        }
        // code
        // 서버측에서 먼저 in.read(100bytes), client가 100 먼저 보내기
        String fileName = file.getName();
        byte[] nameBytes = fileName.getBytes();
        // 100칸 고정(서버측과의 약속)
        byte[] nameBuffer = new byte[100];
        // out.write(100bytes);

        // 파일이름이 100칸보다 길면 안됨
        if (nameBytes.length > 100) {
            System.out.println("File name is too long, it should be less than 100");
            return;
        }
        // 1byte = 1letter in english(ASCII)
        // "test.txt" 8칸 차지, 나머지 92칸 비어있음
        for (int i = 0; i < nameBytes.length; i++) {
            nameBuffer[i] = nameBytes[i];
        }
        // 서버측으로 파일이름부터 보내기 처리
        out.write(nameBuffer);
        out.flush(); // 바로 보내기
        // 파일 내용을 바이트로 변환해서 보내주어야한다
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[4096]; // 4KB
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            out.flush(); // 다 보냈음

            // 서버측에 바이트를 다 받ㅂ으면 메세지를 보내기로함
            byte[] responseBuffer = new byte[1024];
            int responseLength = in.read(responseBuffer);
            String response = new String(responseBuffer, 0, responseLength);
            System.out.println("Server : " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    } // end of uploads

    private void cleanup() {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 실행 흐름 지정 메서드
    public void fileSendRun() {
        try {
            connectToServer();
            setupStreams();
            uploadFile();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            cleanup();
        }

    }

    // main
    public static void main(String[] args) {
        FileClient fileClient = new FileClient("A");
        fileClient.fileSendRun();
    }
} // end of class
