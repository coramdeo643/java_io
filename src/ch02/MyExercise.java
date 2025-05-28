package ch02;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MyExercise {
    public static void main(String[] args) {
        //(my1.txt) as written :
        //My name is Java,
        //What is your name?
        byte[] dataBytes;
        String data = "\nMy name is HTML,\nNice to meet you!";
        try (FileInputStream in = new FileInputStream("my1.txt");
             FileOutputStream fos = new FileOutputStream("my2.txt")) {
            dataBytes = in.readAllBytes(); // my1.txt 읽고 dataBytes 에 저장(복사)
            fos.write(dataBytes); // my2.txt 에 dataBytes 에 저장된 값 붙여넣기

            dataBytes = data.getBytes(); // String data 읽고 dataBytes 에 저장(복사)
            fos.write(dataBytes); // my2.txt 에 dataBytes 에 저장된 값 붙여넣기
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
