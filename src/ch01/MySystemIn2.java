package ch01;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 입력 스트림(in) 키보드에서 프로그램으로 데이터가 들어온다(스트림을 통해서)
 * 기능의 확장을 해보자
 */
public class MySystemIn2 {
    public static void main(String[] args) {

        System.out.println("알파벳 열 개 쓰고 Enter를 누르세요");
        int i;
        try {
            // 키보드에서 엔터는 \n 로 표현이 된다
            while ((i = System.in.read()) != '\n') {
                // 읽은 바이트의 정수값을 출력
                System.out.println("i : " + i);
                System.out.println("문자로 표현 : "+(char)i);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
