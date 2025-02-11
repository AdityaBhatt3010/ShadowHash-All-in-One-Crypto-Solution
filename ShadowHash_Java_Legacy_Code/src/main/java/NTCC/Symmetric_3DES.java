package NTCC;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.security.MessageDigest;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

public class Symmetric_3DES
{
    private static SecretKey secretKey;

    public static void setKey_3DES(String myKey)
    {
        try
        {
            byte[] keyArray = myKey.getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            keyArray = sha.digest(keyArray);
            keyArray = Arrays.copyOf(keyArray, 24); // Use the first 24 bytes for 3DES key

            KeySpec keySpec = new DESedeKeySpec(keyArray);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            secretKey = keyFactory.generateSecret(keySpec);
        }
        catch (Exception e)
        {
            System.out.println("Hashing Error 3-DES");
        }
    }

    public static String encrypt_3DES(String strToEncrypt, String encryptionkey)
    {
        try
        {
            setKey_3DES(encryptionkey);
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] plainTextBytes = strToEncrypt.getBytes("UTF-8");
            byte[] cipherText = cipher.doFinal(plainTextBytes);
            return Base64.getEncoder().encodeToString(cipherText);
        }
        catch (Exception e)
        {
            System.out.println("Encryption Error 3-DES");
            return null;
        }
    }

    public static String decrypt_3DES(String strToDecrypt, String decryptionkey)
    {
        try
        {
            setKey_3DES(decryptionkey);
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] cipherTextBytes = Base64.getDecoder().decode(strToDecrypt);
            byte[] plainText = cipher.doFinal(cipherTextBytes);
            return new String(plainText, "UTF-8");
        }
        catch (Exception e)
        {
            System.out.println("Decryption Error 3-DES");
            return null;
        }
    }

    /*
    public static void main(String[] args)
    {
        String original = "Hello, World!";
        String key = "this_is_a_secret_key";
        String encrypted = encrypt_3DES(original, key);
        String decrypted = decrypt_3DES(encrypted, key);

        System.out.println("Original: " + original);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
    }
    */
}
