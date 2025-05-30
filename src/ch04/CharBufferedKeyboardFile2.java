package ch04;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 문자 기반 스트림을 사용해서 키보드에 입력한 값을 파일에다가 저장.
 * (Append 모드 활성화 + 보조 스트림 버퍼 사용)
 */
public class CharBufferedKeyboardFile2 {
    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try (InputStreamReader isr = new InputStreamReader(System.in);
             BufferedReader br = new BufferedReader(isr);
             FileWriter fw = new FileWriter("mytest.txt", true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            String line;
            String timestamp = String.valueOf(LocalDateTime.now().format(formatter));
            while ((line = br.readLine()) != null && !line.isEmpty()) {
                bw.write("[ " + timestamp + " ] " + line);
                bw.newLine();
                bw.flush();
            }
            System.out.println("Writing the sentence into the file had been finished!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
