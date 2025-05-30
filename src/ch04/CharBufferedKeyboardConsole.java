package ch04;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class CharBufferedKeyboardConsole {
    public static void main(String[] args) {
        /*
        InputStreamReader : System.in(InputStream) 기반으로 사용
        BufferedReader : InputStreamReader 를 wrap 해서 사용
        */

        try (InputStreamReader isr = new InputStreamReader(System.in);
             BufferedReader br = new BufferedReader(isr);
             PrintWriter pw = new PrintWriter(System.out);
             BufferedWriter bw = new BufferedWriter(pw)) {
            System.out.println("텍스트를 입력하세요(종료 빈줄입력)");
            /*
            readLine() method : 문자열의 한 줄을 그대로 읽음
            null : 입력의 끝을 의미함(혹시 인텔리제이에서 종료가 안될때 Ctrl + D 또는 Ctrl + z)
             */
            String line;
            while ((line = br.readLine()) != null && !line.isEmpty()) {
                bw.write(line.replace("자바", "JAVA"));
                bw.newLine();
                bw.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("스트림 사용중 오류발생 : " + e.getMessage());
        }

    }
}
