package ch04;

import java.io.*;

/**
 * 문자 기반 스트림을 사용해서 키보드에 입력한 값을 파일에다가 저장.
 * (Append 모드 활성화 + 보조 스트림 버퍼 사용)
 */
public class CharBufferedKeyboardFile {
    public static void main(String[] args) {
        writeToFile("mytest.txt");
    }

    public static void writeToFile(String filename) {
        try (InputStreamReader isr = new InputStreamReader(System.in);
             BufferedReader br = new BufferedReader(isr);
             FileWriter fw = new FileWriter(filename, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            String line;
            while ((line = br.readLine()) != null && !line.isEmpty()) {
                bw.write(line);
                bw.newLine();
                bw.flush();
            }
            System.out.println("Writing the sentence into the ' " + filename + " ' had been finished!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
