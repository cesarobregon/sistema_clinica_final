package miselaneos;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;

/**
 *
 * @author cesar
 * //http://www.jasypt.org/encrypting-texts.html
 */
public class Encripta {
    private static String key = "semilla";
    
    //metodo para encriptar
    public String encryt(String plainText) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(key);
        String result = plainText;
        try {
            result = encryptor.encrypt(plainText);
        } catch (EncryptionOperationNotPossibleException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    //metodo para desencriptar
    public String desencrypt(String encPassword) {
        String result = null;        
        StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();
        try {
            decryptor.setPassword(key);
            result = decryptor.decrypt(encPassword);
        } catch (EncryptionOperationNotPossibleException ex) {
        }
        return result;
    }
}
