package ch03;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileStreamBasic {

    public static void main(String[] args) {
        // 함수 호출 call the function
        writeToFile("basic_output.txt");
        // 함수 호출 : 파일에서 텍스트 읽기
//        readFromFile("basic_output.txt");
    } // end of main

    // 파일에 텍스트를 쓰는 함수(문자 기반 스트림 사용)
    public static void writeToFile(String fileName) {
        /*
          FileWriter : 문자 기반 출력 스트림으로, 텍스트를 파일에 기록할수있다
         */
        try (FileWriter writer = new FileWriter(fileName)) {
            // File에 기록할 텍스트 선언
            String text = "Java Text based stream example\n";
            writer.write(text); // 파일 없다면 new file 생성 + write the text into the file
            writer.write("추가 문자열을 기록합니다");
            // 스트림을 비워주자(FLUSH)
            writer.flush();
            System.out.println("파일에 텍스트를 잘 기록했어요");
        } catch (IOException e) {
            System.out.println("파일 쓰기 중 오류 발생" + e.getMessage());
        }
    }

    public static void readFromFile(String fileName) {
        /*
        FileReader : Text based input stream, 파일에서 텍스트를 읽음
         */
        try(FileReader reader = new FileReader(fileName)) {
            // read() method : 한문자씩 읽어 유니코드 값(정수)로 반환
//            int charCode = reader.read();
//            System.out.println(charCode);
//            System.out.println((char)charCode);

            // 파일에 모든 텍스트를 읽을수있도록 코딩하자, -1 = 파일의 끝을 의미함
            int charCode;
            while ((charCode = reader.read()) != -1) {
                System.out.print((char)charCode);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
