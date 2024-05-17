package util;

import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class FileEncryptDecryptUtil {

    public void encryptImage(String imagePath, String outputPath, String pubKey){
        try {
            byte[] publicKeyBytes = Base64.getDecoder().decode(pubKey); // decoding the public key
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA"); // get
            PublicKey pubbKey = keyFactory.generatePublic(keySpec);

            // using RSA cipher for encryption
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubbKey);

            try (FileInputStream fis = new FileInputStream(imagePath); // encrypt the imagePath and write into outputPath file
                 FileOutputStream fos = new FileOutputStream(outputPath)) {

                byte[] inputBytes = new byte[117];
                int bytesRead;
                while ((bytesRead = fis.read(inputBytes)) != -1) {
                    byte[] outputBytes = cipher.doFinal(inputBytes, 0, bytesRead);
                    fos.write(outputBytes);
                }
            }

            System.out.println("===========================");
            System.out.println("File Encryption Done!");
            System.out.println("===========================");
        } catch (Exception e) {
            System.out.println("Exception Occurred: "+ e.getMessage());
        }

    }

    public void decryptImage(String encodedImagePath, String decodedImagePath, String pvtKey){
        try {

            byte[] privateKeyBytes = Base64.getDecoder().decode(pvtKey); // decoding pvt key
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

            // Initialize the cipher for decryption
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            try (FileInputStream fis = new FileInputStream(encodedImagePath);
                 FileOutputStream fos = new FileOutputStream(decodedImagePath)) {

                byte[] inputBytes = new byte[256];
                int bytesRead;
                while ((bytesRead = fis.read(inputBytes)) != -1) {
                    byte[] outputBytes = cipher.doFinal(inputBytes, 0, bytesRead);
                    fos.write(outputBytes);
                }
            }

            System.out.println("====================================================================================");
            System.out.println("File Decryption Done!, Please check on /src/resources folder named with decoded.JPG");
            System.out.println("=====================================================================================");
        } catch (Exception e) {
            System.out.println("Exception Occurred"+ e.getMessage());
        }


    }

}
