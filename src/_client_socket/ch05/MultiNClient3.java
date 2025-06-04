package _client_socket.ch05; // 이 코드가 속한 패키지입니다. 클라이언트 코드를 위한 폴더와 같아요.

import java.io.BufferedReader; // 텍스트를 효율적으로 읽기 위한 클래스입니다.
import java.io.IOException; // 입출력 작업 중 발생할 수 있는 오류를 처리하기 위한 클래스입니다.
import java.io.InputStreamReader; // 바이트 스트림을 문자 스트림으로 변환해주는 클래스입니다.
import java.io.PrintWriter; // 텍스트를 효율적으로 쓰기 위한 클래스입니다.
import java.net.Socket; // 클라이언트와 서버 간의 통신을 위한 소켓 클래스입니다.

public class MultiNClient3 { // 다중 클라이언트 서버에 접속하는 클라이언트 클래스입니다.
    private final int PORT = 5000; // 서버가 대기하는 포트 번호입니다. 서버와 동일해야 합니다.
    private final String name; // 클라이언트의 이름을 저장하는 변수입니다. (현재 코드에서는 사용되지 않음)
    private Socket socket; // 서버와 연결된 소켓입니다.
    private PrintWriter wStream; // 서버에게 데이터를 보낼 때 사용하는 스트림입니다.
    private BufferedReader rStream; // 서버로부터 데이터를 읽을 때 사용하는 스트림입니다.
    private BufferedReader kbReader; // 키보드로부터 입력을 읽을 때 사용하는 스트림입니다.

    // 클라이언트 객체를 생성할 때 이름을 전달받습니다.
    public MultiNClient3(String name) {
        this.name = name;
    }

    // 서버에 연결하는 메서드입니다.
    private void connect() throws IOException {
        // "localhost"는 현재 실행 중인 컴퓨터를 의미하며, PORT(5000)로 서버에 접속을 시도합니다.
        socket = new Socket("localhost", PORT);
        System.out.println("서버에 연결되었습니다."); // 연결 성공 메시지를 출력합니다.
    }

    // 입출력 스트림을 설정하는 메서드입니다.
    private void setup() throws IOException {
        // 서버로 데이터를 보내기 위한 스트림을 설정합니다. (자동 플러시 활성화)
        wStream = new PrintWriter(socket.getOutputStream(), true);
        // 서버로부터 데이터를 읽기 위한 스트림을 설정합니다.
        rStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        // 키보드(콘솔)로부터 사용자 입력을 읽기 위한 스트림을 설정합니다.
        kbReader = new BufferedReader(new InputStreamReader(System.in));
    }

    // 서버로부터 메시지를 읽는 스레드를 생성하는 메서드입니다.
    private Thread createRThread() {
        return new Thread(() -> { // 람다 표현식으로 스레드의 run 메서드를 정의합니다.
            String serverMsg; // 서버로부터 받은 메시지를 저장할 변수입니다.
            try {
                // 서버로부터 한 줄씩 메시지를 읽습니다.
                // 서버가 연결을 끊으면 readLine()은 null을 반환합니다.
                while ((serverMsg = rStream.readLine()) != null) {
                    System.out.println(serverMsg); // 받은 메시지를 콘솔에 출력합니다.
                }
            } catch (IOException e) { // 입출력 작업 중 오류가 발생하면 예외를 잡습니다.
                e.printStackTrace(); // 오류의 스택 트레이스를 출력합니다.
            }
        });
    }

    // 키보드 입력을 읽어 서버로 메시지를 보내는 스레드를 생성하는 메서드입니다.
    private Thread createWThread() {
        return new Thread(() -> { // 람다 표현식으로 스레드의 run 메서드를 정의합니다.
            String kbMsg; // 키보드로부터 입력받은 메시지를 저장할 변수입니다.
            try {
                // 키보드로부터 한 줄씩 입력을 읽습니다.
                // 사용자가 EOF(Ctrl+Z 또는 Ctrl+D)를 보내면 readLine()은 null을 반환합니다.
                while ((kbMsg = kbReader.readLine())!= null) {
                    wStream.println(kbMsg); // 읽은 메시지를 서버로 전송합니다.
                }
            } catch (IOException e) { // 입출력 작업 중 오류가 발생하면 예외를 잡습니다.
                e.printStackTrace(); // 오류의 스택 트레이스를 출력합니다.
            }
        });
    }

    // 연결 종료 시 자원을 정리하는 메서드입니다.
    private void cleanup() {
        try {
            if(socket != null) { // 소켓이 열려 있다면
                socket.close(); // 소켓을 닫아 연결을 종료하고 자원을 해제합니다.
            }
        } catch (IOException e) { // 소켓을 닫는 중 오류가 발생하면 예외를 잡습니다.
            e.printStackTrace(); // 오류의 스택 트레이스를 출력합니다.
        }
    }

    // 읽기 스레드와 쓰기 스레드를 시작하고, 이들이 종료될 때까지 기다리는 메서드입니다.
    private void startComm() throws InterruptedException {
        Thread rThread = createRThread(); // 서버 메시지 읽기 스레드를 생성합니다.
        Thread wThread = createWThread(); // 키보드 입력 및 서버 메시지 쓰기 스레드를 생성합니다.
        rThread.start(); // 읽기 스레드를 시작합니다.
        wThread.start(); // 쓰기 스레드를 시작합니다.
        rThread.join(); // 읽기 스레드가 종료될 때까지 현재 스레드를 대기시킵니다.
        wThread.join(); // 쓰기 스레드가 종료될 때까지 현재 스레드를 대기시킵니다.
    }

    // 클라이언트의 채팅 기능을 실행하는 메인 메서드입니다.
    public void chatRun() {
        try {
            connect(); // 서버에 연결합니다.
            setup(); // 입출력 스트림을 설정합니다.
            startComm(); // 읽기/쓰기 스레드를 시작하고 통신을 진행합니다.
        } catch (IOException | InterruptedException e) { // 연결 또는 스레드 실행 중 발생할 수 있는 예외를 잡습니다.
            e.printStackTrace(); // 오류의 스택 트레이스를 출력합니다.
        } finally { // try 블록이 성공적으로 실행되든 예외가 발생하든 항상 실행되는 블록입니다.
            cleanup(); // 자원을 정리합니다.
        }
    }

    // 프로그램의 시작점입니다.
    public static void main(String[] args) {
        System.out.println("client"); // 클라이언트가 시작되었음을 알리는 메시지를 콘솔에 출력합니다.
        // MultiNClient2 객체를 생성합니다. (현재는 이름이 빈 문자열로 초기화됨)
        MultiNClient2 nClient2 = new MultiNClient2("");
        nClient2.chatRun(); // 채팅 기능을 실행합니다.
    }
}
