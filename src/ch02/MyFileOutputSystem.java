package ch02;

import java.io.FileOutputStream;

/**
 * 파일 출력 스트림을 사용해보자
 */
public class MyFileOutputSystem {
    public static void main(String[] args) {
        String data = "\nHello, Java FileOutputSystem! abc abc 안녕 반가워~ 毎日";
        // new FileOutputStream <-- 파일 없으면 새로 생성해서 데이터 작성
        // append 모드 활성화(true)
        // new FileOutputStream <-- 파일 없으면 새로 생성해서 데이터 작성 / 있으면 거기에 추가 작성
        try (FileOutputStream fos = new FileOutputStream("output.txt", true)) {
            // 문자열 data 값을 byte 배열로 변환 시켜보자~
            byte[] dataBytes = data.getBytes();
            // [72, 111, 123, 15, 132, ...]
            // 바이트 단위로 파일에 데이터를 쓴다
            fos.write(dataBytes);
            System.out.println("파일 출력 완료 : output.txt");
            // 참고 : output.txt 파일을 열었을 때 텍스트로 보이는 이유는
            // 에디터가 바이트 데이터를 문자로 해석해서 보여줬기 때문이야~
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
