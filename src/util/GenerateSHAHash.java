package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GenerateSHAHash {
    public String generateHash(String imagePath){
        try (FileInputStream fis = new FileInputStream(imagePath)) {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] byteArray = new byte[1024];
            int bytesCount;
            while ((bytesCount = fis.read(byteArray)) != -1) {
                digest.update(byteArray, 0, bytesCount);
            }

            byte[] bytes = digest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException | IOException e) {
            System.out.println("Exception Occurred"+ e.getMessage());
        }

        return  "";
    }
}
