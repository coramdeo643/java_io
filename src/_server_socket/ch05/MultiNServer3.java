// MultiNServer2.java 코드와 주석을 포함하는 문자열입니다.
// 이 문자열은 실제 Java 코드를 나타내며, 주석이 포함되어 있습니다.

package _server_socket.ch05; // 이 코드가 속한 패키지입니다. 일종의 폴더와 같아요.

import java.io.BufferedReader; // 텍스트를 효율적으로 읽기 위한 클래스입니다.
import java.io.IOException; // 입출력 작업 중 발생할 수 있는 오류를 처리하기 위한 클래스입니다.
import java.io.InputStreamReader; // 바이트 스트림을 문자 스트림으로 변환해주는 클래스입니다.
import java.io.PrintWriter; // 텍스트를 효율적으로 쓰기 위한 클래스입니다.
import java.net.ServerSocket; // 서버 소켓을 생성하고 클라이언트의 연결을 기다리는 클래스입니다.
import java.net.Socket; // 클라이언트와 서버 간의 통신을 위한 소켓 클래스입니다.
import java.util.Vector; // 동기화된(안전한) 리스트로, 여러 스레드에서 동시에 접근해도 안전합니다.

public class MultiNServer3 { // 다중 클라이언트 접속을 처리하는 서버 클래스입니다.
    private static int count = 0; // 현재 접속한 클라이언트 수를 세는 변수입니다.
    private static final int PORT = 5000; // 서버가 클라이언트의 연결을 기다릴 포트 번호입니다.
    // 모든 클라이언트에게 메시지를 보낼 수 있는 PrintWriter 객체들을 저장하는 벡터입니다.
    // 새로운 클라이언트가 접속하면 여기에 추가되고, 연결이 끊기면 제거됩니다.
    private static Vector<PrintWriter> clientWriters = new Vector<>();

    // 각 클라이언트의 요청을 개별적으로 처리하는 내부 클래스입니다.
    // Thread를 상속받아 동시성을 확보합니다.
    public static class ClientHandler extends Thread {
        private Socket socket; // 현재 클라이언트와 연결된 소켓입니다.
        private PrintWriter out; // 클라이언트에게 데이터를 보낼 때 사용하는 스트림입니다.
        private BufferedReader in; // 클라이언트로부터 데이터를 읽을 때 사용하는 스트림입니다.

        // ClientHandler 객체를 생성할 때 클라이언트 소켓을 전달받습니다.
        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override // Thread 클래스의 run 메서드를 오버라이드합니다. 스레드가 시작될 때 실행되는 코드입니다.
        public void run(){
            try {
                // 클라이언트로부터 데이터를 읽기 위한 스트림을 설정합니다.
                // InputStreamReader는 바이트 스트림(Socket.getInputStream())을 문자 스트림으로 변환하고,
                // BufferedReader는 문자 스트림을 효율적으로 읽을 수 있게 합니다.
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                // 클라이언트에게 데이터를 보내기 위한 스트림을 설정합니다.
                // PrintWriter는 텍스트 데이터를 편리하게 보낼 수 있게 하며,
                // 'true'는 자동 플러시(auto-flush)를 의미하여, println() 호출 시 즉시 데이터가 전송됩니다.
                out = new PrintWriter(socket.getOutputStream(), true);

                clientWriters.add(out); // 현재 클라이언트에게 메시지를 보낼 수 있는 스트림을 전체 목록에 추가합니다.

                String msg; // 클라이언트로부터 받은 메시지를 저장할 변수입니다.
                // 클라이언트로부터 한 줄씩 메시지를 읽습니다.
                // 클라이언트가 연결을 끊거나 메시지를 보내지 않으면 readLine()은 null을 반환합니다.
                while ((msg = in.readLine()) != null) {
                    System.out.println(msg); // 서버 콘솔에 클라이언트로부터 받은 메시지를 출력합니다.
                    // 현재 접속해 있는 모든 클라이언트에게 메시지를 다시 보냅니다 (에코 또는 채팅 기능).
                    for (PrintWriter writer : clientWriters) {
                        writer.println(msg); // 각 클라이언트에게 메시지를 전송합니다.
                    }
                }
            } catch (IOException e) { // 입출력 작업 중 오류가 발생하면 예외를 잡습니다.
                e.printStackTrace(); // 오류의 스택 트레이스를 출력하여 디버깅에 도움을 줍니다.
                System.out.println(e.getMessage()); // 오류 메시지를 출력합니다.
            } finally { // try 블록이 성공적으로 실행되든 예외가 발생하든 항상 실행되는 블록입니다.
                try {
                    // 클라이언트 소켓이 null이 아니면 닫습니다.
                    // 소켓을 닫으면 연결이 종료되고 관련 자원이 해제됩니다.
                    if(socket != null) socket.close();
                    // 현재 클라이언트에게 메시지를 보낼 수 있는 스트림을 전체 목록에서 제거합니다.
                    // 이 클라이언트는 더 이상 메시지를 받을 수 없기 때문입니다.
                    clientWriters.remove(out);
                } catch (IOException e) { // 소켓을 닫는 중 오류가 발생하면 예외를 잡습니다.
                    e.printStackTrace(); // 오류의 스택 트레이스를 출력합니다.
                    System.out.println(e.getMessage()); // 오류 메시지를 출력합니다.
                }
            }
        }
    } // ClientHandler 클래스 종료

    public static void main(String[] args) { // 프로그램의 시작점입니다.
        System.out.println("server"); // 서버가 시작되었음을 알리는 메시지를 콘솔에 출력합니다.
        // try-with-resources 구문을 사용하여 ServerSocket이 자동으로 닫히도록 합니다.
        try (ServerSocket serverSocket = new ServerSocket(PORT)) { // 지정된 포트(5000)로 새 ServerSocket을 생성합니다.
            while (true) { // 서버를 무한 루프 상태로 만들어 계속해서 클라이언트의 연결을 받도록 합니다.
                // 클라이언트의 연결 요청을 기다립니다. 연결 요청이 오면 새로운 Socket 객체를 반환합니다.
                // 이 Socket은 특정 클라이언트와의 통신을 담당합니다.
                // 새로운 ClientHandler 스레드를 생성하고 시작하여 해당 클라이언트와의 통신을 별도로 처리합니다.
                new ClientHandler(serverSocket.accept()).start();
                count++; // 클라이언트가 하나 접속할 때마다 카운트를 증가시킵니다.
                System.out.println("count = " + count); // 현재 접속 클라이언트 수를 출력합니다.
            }
        } catch (Exception e) { // 서버 운영 중 발생할 수 있는 모든 예외를 잡습니다.
            e.printStackTrace(); // 오류의 스택 트레이스를 출력합니다.
            System.out.println(e.getMessage()); // 오류 메시지를 출력합니다.
        }
    } // main 메서드 종료
} // MultiNServer2 클래스 종료
