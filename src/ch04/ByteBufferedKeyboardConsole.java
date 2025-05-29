package ch04;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;

/**
 * 보조 기반 스트림에 대해서 알아보자
 * 기반 스트림이 있어야 사용할수있다
 * (InputStream, InputStreamReader, outputStream, OutputStreamWriter)
 */
public class ByteBufferedKeyboardConsole {
    public static void main(String[] args) {
        // 바이트 기반 스트림 + 버퍼드 스트림
        try (BufferedInputStream bis = new BufferedInputStream(System.in);
             BufferedOutputStream bos = new BufferedOutputStream(System.out)) {
            /*
            보조 스트림을 활용해서 한번에 1024 byte 크기의 버퍼 배열로 데이터를 읽자
             */
            // Buffer tool prepare
            byte[] buffer = new byte[1024];
            int bytesRead;

            // bis.read(); // read by 1 byte
            // bis.read(buffer); // read by 1024 bytes

            while ((bytesRead = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
