package ch03;

import java.io.*;

/**
 * 바이트 단위 스트림 이름 형태
 * InputStream(System.in), OutputStream(System.out)
 * 문자 기반 스트림의 이름 형태
 * xxxReader, xxxWriter(문자 기반 형태 네이밍 형식)
 */
public class KeyboardConsoleStream {
    public static void main(String[] args) {
        /**
         * InputStreamReader 의 reader() 메서드는 하나의 문자를 읽어서
         * 그 문자의 유니코드(UTF-8, UTF-16)
         * 값으로(정수값) 반한한다
         */

        // 프로그램 목표 : 키보드에서 문자 기반의 스트림을 사용해서 코드를 데이터로 읽어보자
        try (InputStreamReader reader = new InputStreamReader(System.in);
             PrintWriter writer = new PrintWriter(System.out)
        ) {
            System.out.println("텍스트를 입력하세요(종료하려면 ctrl + D)");
//            int charCode = reader.read();
//            System.out.println("-- - - - -  - - - - - - - -");
//            System.out.println(charCode);
            int charCode;
            while ((charCode = reader.read()) != -1 ) {
//                System.out.println((char)charCode);
                writer.println((char)charCode);
            }
            /**
             * 입력된 문자를 콘솔에 출력하는데 버퍼를 즉시 비움
             */
            writer.flush(); // 즉시 출력
        } catch(IOException e) {
            /**
             * 예 : 키보드 입력 오류, 콘솔 출력 오류가 여기서 catch 됨
             */
            System.out.println("입출력중 오류 발생");
        }
    } // end of main
}
