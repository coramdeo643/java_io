package _server_socket.ch06;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 1:1 socket file server
 */
public class SimpleFileServer {

    private static final int PORT = 5000;
    private static final String UPLOAD_DIR = "Uploads/";

    private static void handleClient(Socket socket) {
        try (InputStream in = socket.getInputStream();
             OutputStream out = socket.getOutputStream()) {
            // Thinking the flow = (Client, File name + mass of bytes)
            byte[] nameBuffer = new byte[100];
            in.read(nameBuffer);
            String filename = new String(nameBuffer).trim();
            System.out.println("File name : " + filename);
            // how to create the file?
            // File object on memory
            File file = new File(UPLOAD_DIR + filename);
            FileOutputStream fos = new FileOutputStream(file);
            // Buffer to read and write the file(4KB size = 4096 bytes)
            // 8 bit = 1 byte / 1,024 bytes = 1 KB / 1,024 KB = 1 MB / 1,024 MB = 1 GB
            // memory size should be expressed by 2 squares (2-4-8-16-32-64-128-256-512-1024) (1024 = 2^10)
            byte[] buffer = new byte[4096];
            int bytesRead;
            // EOF = end of file = -1
            while ((bytesRead = in.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
                if(bytesRead < 4096) {
                    break;
                }
            }
            System.out.println("Save completed in the Server_com" + filename);
            out.write("File upload completed : ".getBytes());
            out.write(filename.getBytes());
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // socket.close();
        }
    }

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.err.println("Waiting the Client's connection...");
            try (Socket connectedClientSocket = serverSocket.accept()) {
                System.err.println("Connected with the Client...");
                handleClient(connectedClientSocket);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    } // end of main
} // end of class
