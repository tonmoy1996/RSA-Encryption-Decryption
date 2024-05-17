package util;

import model.KeyPairs;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAKeyGenerator {

    public KeyPairs generatePuPvtKey(String pubKeyPath, String pvtKeyPath){

        KeyPairs pairs= new KeyPairs();
        try {
            if (!Files.exists(Paths.get(pubKeyPath)) && !Files.exists(Paths.get(pvtKeyPath))) {
                System.out.println("Generating New Pairs of keys...");
                return generateNewKeys();
            }
            System.out.println("Using Existing Pairs of keys...");
            //read from files
            PublicKey publicKey= readPubKey(pubKeyPath);
            PrivateKey privateKey= readPvtKey(pvtKeyPath);
            pairs.setPubKey(Base64.getEncoder().encodeToString(publicKey.getEncoded()));
            pairs.setPvtKey(Base64.getEncoder().encodeToString(privateKey.getEncoded()));
            return pairs;

        } catch (Exception exception) {
            System.out.println("Uncaught Exception");
        }
        return null;
    }

    public KeyPairs generateNewKeys() throws IOException, NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        PublicKey publicKey = keyPair.getPublic(); // fetch public key
        PrivateKey privateKey = keyPair.getPrivate(); // fetch private key

        // save to file
        saveKeyToFile(publicKey,privateKey);
        String pubString = Base64.getEncoder().encodeToString(publicKey.getEncoded()); // encode to base 64
        String pvtString = Base64.getEncoder().encodeToString(privateKey.getEncoded());

        return new KeyPairs(pubString,pvtString);
    }

    public void saveKeyToFile(PublicKey publicKey,PrivateKey privateKey) throws IOException {
        // save to file
        try (FileOutputStream fos = new FileOutputStream("src/resources/publicKey.key")) {
            fos.write(Base64.getEncoder().encode(publicKey.getEncoded()));
        }catch (Exception ex){
            System.out.println("Exception During Save to File.");
        }

        // Save the private key to a file
        try (FileOutputStream fos = new FileOutputStream("src/resources/privateKey.key")) {
            fos.write(Base64.getEncoder().encode(privateKey.getEncoded()));
        } catch (Exception ex){
            System.out.println("Exception During Save to File.");
        }
    }

    public PublicKey readPubKey(String pubKeyPath) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = Files.readAllBytes(Paths.get(pubKeyPath));
        keyBytes = Base64.getDecoder().decode(keyBytes);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }
    public PrivateKey readPvtKey(String pvtKeyPath) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = Files.readAllBytes(Paths.get(pvtKeyPath));
        keyBytes = Base64.getDecoder().decode(keyBytes);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(spec);
    }
}
