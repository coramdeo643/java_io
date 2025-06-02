package _server_socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 서버측 코드
 * 간단한 네트워크를 통한 서버측 프로그래밍에 필요한 준비물
 * 1. 서버 소켓이 필요
 * 2. IP와 포트번호가 필요
 * 3. 사전 기반 지식 ( 잘 알려진 포트 번호  - 주로 시스템 레벨에서 선점 포트 번호
 */
public class ServerFile {
    public static void main(String[] args) {

        // 소켓 통신을 하기 위해서 (서버측)
        // 1. 서버 소켓이 필요하다
        // 서버 소켓 선언
       ServerSocket serverSocket = null;

       try {
           // 내가 만든 서버 소켓 프로그래밍에 포트번호 5000번을 항당한다
           // 단 다른 프로그램이 5000번 포트를 선점하고있다면 에러 발생
           serverSocket = new ServerSocket(5001);
           System.out.println("서버를 시작합니다, 포트번호 = 5001");
           // 클라이언트 측 연결을 기다립니다.
           // 내부적으로 while 대기 중(클라이언트 연결 요청할때 까지)
           Socket clientSocket = serverSocket.accept();
           // Client와 연결이 되면 서로 서버측 Socket이 생성되고 Client Socket 연결됨
           System.out.println(">>> Client connected <<<");
           // Client 에서 보낸 데이터를 읽기 위한 입력 스트림 필요
           // InputStream input = clientSocket.getInputStream(); // Byte 단위로 데이터 읽을수있음
           // 문자 기반의 스트림으로 확장
           // 소켓에서 입력 스트림 받음>인풋스트림에서 버퍼드로 받음>리더로 읽음
           BufferedReader reader
                   = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
           // Client 가 보낸 데이터 한줄 기반으로 읽어보기
           String msg;
           while (true) {
               msg = reader.readLine();
               if (msg == null) {
                   break;
               }
               System.out.println("Message from client = " + msg);

           }


       } catch (IOException e) {
           e.printStackTrace();
       } finally {
           if (serverSocket != null) {
               try {
                   serverSocket.close();
               } catch (Exception e) {
                   System.out.println("Error during serverSocket closing");
               }
           }
       }
    }
}
