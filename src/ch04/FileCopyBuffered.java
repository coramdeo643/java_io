package ch04;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.spec.RSAOtherPrimeInfo;

public class FileCopyBuffered {
    public static void main(String[] args) {
        //파일 경로 지정
        String sourceFilePath = "employees.zip";
        String destinationFilePath = "employees_copy22.zip";
        // 소요시간 측정
        long startT= System.nanoTime();

        try(FileInputStream fis = new FileInputStream(sourceFilePath);
            FileOutputStream fos = new FileOutputStream(destinationFilePath);
            BufferedInputStream bfis = new BufferedInputStream(fis);
            BufferedOutputStream bfos = new BufferedOutputStream(fos)) {
            byte[] bytes = new byte[1024];
            int data;
            while ((data = bfis.read(bytes)) != -1) {
                bfos.write(bytes, 0, data);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("파일복사중오류발생" + e.getMessage());
        }
        long endTime = System.nanoTime();
        long duration = endTime - startT;
        System.out.println(duration);
        System.out.println(duration / 1000000000);
    }
}
