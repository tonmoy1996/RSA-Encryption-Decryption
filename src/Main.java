import model.KeyPairs;
import util.GenerateSHAHash;
import util.RSAKeyGenerator;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {

        // image and key paths
        String inputFilePath = "src/resources/AMD_image_file.JPG";
        String encodedImagePath = "src/resources/encoded.JPG";
        String decodedImagePath = "src/resources/decoded.JPG";

        // Keys Path
        String pubKeyPath = "src/resources/publicKey.key";
        String pvtKeyPath = "src/resources/privateKey.key";


        //1. Key Generation
        RSAKeyGenerator keyGenerator= new RSAKeyGenerator();
        KeyPairs keys= keyGenerator.generatePuPvtKey(pubKeyPath,pvtKeyPath);

        //print keys
        System.out.println(keys.toString());;

        // 2. SHA-256 Hash Generation
        GenerateSHAHash shaHash= new GenerateSHAHash();
        String hashImg= shaHash.generateHash(inputFilePath);
        System.out.println("SHA-256 Hash Image: "+ hashImg);

        //3.  generate Public and Private Key, Please locate the image inside /resources folder
        CryptographyService cryptographyService = new CryptographyService();

        // encrypt and decrypt image
        cryptographyService.encrypt(inputFilePath, encodedImagePath, keys.getPubKey());
        cryptographyService.decrypt(encodedImagePath, decodedImagePath, keys.getPvtKey());


    }


}