package _client_socket.ch03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientFile03 {
    public static void main(String[] args) {
        Socket socket = null;
        Scanner scanner = new Scanner(System.in);
        try {
            socket = new Socket("localhost", 5000);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            String msg;
            msg = scanner.nextLine();
            while (true) {
                writer.write(msg+"\n");
                writer.flush();
                if ("exit".equalsIgnoreCase(msg)) {
                    break;
                }
            }

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response;
            response = reader.readLine();
            System.out.println(response+"read");
            //System.out.println("From server : " + response);
            //while (true) {
//                if ("exit".equalsIgnoreCase(msg)) {
//                    break;
//                }
//                if(response==null) {
//                    break;
//                }
            //}
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Error from client socket closing");
                    e.printStackTrace();
                }
            }
        }
    }
}
