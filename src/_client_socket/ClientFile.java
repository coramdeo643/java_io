package _client_socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Client Code
 * 1. Need to know IP address and port number of Server
 * 2. Socket needed(network 통신 표준규약을 지켜야하니까)
 * 3. Server 측으로 Data 전달하려면 출력 스트림이 필요
 *
 */
public class ClientFile {
    public static void main(String[] args) {
        // 생성자 = 연결하고자 하는 컴퓨터 IP 주소 + port number 필요
        // 만약 내 컴퓨터에 접근하고 싶다면 localhost 사용가능
        // 강사님 IP = 192.168.0.132.
        Socket socket = null;
        try {
            socket = new Socket("localhost", 5001);
            // 서버로 데이터를 보내기 위한 준비물이 필요
            // 출력 스트림 필요(문자)
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);
            String msg;
            while (true) {
                msg = scanner.nextLine();
                if("exit".equalsIgnoreCase(msg)) {
                    break;
                }
                writer.println(msg);
                writer.flush();
            }

            scanner.close();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if(socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
