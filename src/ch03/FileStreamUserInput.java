package ch03;

import java.io.*;

/**
 * 문자 기반 스트림을 사용하자.
 * 1. 키보드에서 값을 받아서 파일에 쓰기
 * 2. 다시 그 파일을 읽는 함수를 만들어서 실행하기
 */
public class FileStreamUserInput {
    public static void main(String[] args) {
        writeUserInputToFile("user_input.txt");
//        readFromFile("basic_output.txt");

    } // end of main

    // 키보드에서 입력을 받아 파일에 쓰는 함수를 만들어 보세요
    public static void writeUserInputToFile(String fileName) {
        try (InputStreamReader reader = new InputStreamReader(System.in);
             FileWriter writer = new FileWriter(fileName, true)
        ) {
            System.out.println("Type text here : ");
            int charCode;
            while ((charCode = reader.read()) != -1) {
                writer.write(charCode);
                writer.flush(); // 즉시 출력
            }
            System.out.println(fileName + " 파일에 텍스트를 모두 적었어요");
        } catch (IOException e) {
            System.out.println("입출력중 오류 발생");
        }
    }
    // 파일에서 텍스트를 읽는 함수 만들어 보세요
    public static void readFromFile(String fileName) {
        try(FileReader reader = new FileReader(fileName)) {
            int charCode;
            while ((charCode = reader.read()) != -1) {
                System.out.print((char)charCode);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
