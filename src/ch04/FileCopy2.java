package ch04;

import java.io.*;

public class FileCopy2 {
    public static void main(String[] args) {
        String sourceFP = "employees.zip";
        String destinFP = "employees_copy2.zip";
        long startT = System.nanoTime();
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sourceFP));
             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destinFP))) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        long endT = System.nanoTime();
        long dur = endT - startT;
        double sec = dur / 1000_000_000.0;
        System.out.println("dur = " + dur);
        System.out.println("sec = " + sec);
    }
}
