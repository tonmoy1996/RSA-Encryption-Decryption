import util.FileEncryptDecryptUtil;

public class CryptographyService {

    public  FileEncryptDecryptUtil util;
    public CryptographyService(){
        util= new FileEncryptDecryptUtil();
    }

    public void encrypt(String imagePath, String outputPath, String pubKey){
        this.util.encryptImage(imagePath,outputPath,pubKey);
    }
    public void decrypt(String imagePath, String outputPath, String pvtKey){
        this.util.decryptImage(imagePath,outputPath,pvtKey);
    }

}
