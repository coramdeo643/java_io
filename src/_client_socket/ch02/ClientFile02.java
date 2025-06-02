package _client_socket.ch02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Client code
 * 1:1 양방향 통신 구현
 */
public class ClientFile02 {
    public static void main(String[] args) {
        /* 1. IP address + port number
        2. socket
        3. OutputStream
        4. InputStream */
        Socket socket = null;
        try {
            socket = new Socket("localhost", 5000);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            // Write
            writer.write("Hello, Server!!!\n");
            writer.flush();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(socket.getInputStream()));


            // Read
            String response;
            response = reader.readLine();
            System.out.println("From server : " + response);


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(socket != null) {
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
