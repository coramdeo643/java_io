package _server_socket.ch06;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class MultiFileServer {
    private static int count = 0;
    private static final int PORT = 5000;
    private static final String UPLOAD_DIR = "Uploads/";

    public static class ClientHandler extends Thread {
        private Socket socket;
        private OutputStream out;
        private InputStream in;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }
        @Override
        public void run(){
            try {
                in = socket.getInputStream();
                out = socket.getOutputStream();

                byte[] nameBuffer = new byte[100];
                in.read(nameBuffer);
                String filename = new String(nameBuffer).trim();
                System.out.println("File name : " + filename);
                File file = new File(UPLOAD_DIR + filename);
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
                System.out.println(filename + " saved");
                out.write("File".getBytes());
                out.write(filename.getBytes());
                out.flush();
            } catch (IOException e) {
                e.printStackTrace(); System.out.println(e.getMessage());
            } finally {

            }
        }
    } // handler

    public static void main(String[] args) {
        System.out.println("server");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                new MultiFileServer.ClientHandler(serverSocket.accept()).start();
                count++;
                System.out.println("count = " + count);
            }
        } catch (Exception e) {
            e.printStackTrace(); System.out.println(e.getMessage());
        }

    } // main
}
