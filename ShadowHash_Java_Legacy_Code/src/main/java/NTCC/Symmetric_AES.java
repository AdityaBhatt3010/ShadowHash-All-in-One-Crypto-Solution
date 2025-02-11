package NTCC;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

public class Symmetric_AES
{
    private static SecretKeySpec secretKey;
    private static byte[] key;

    public static void setKey_AES(String myKey)
    {
        try
        {
            key = myKey.getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);

            secretKey = new SecretKeySpec(key, "AES");
            //System.out.println("Key: " + secretKey);
        }
        catch (Exception e)
        {
            System.out.println("Hashing Error AES");
        }
    }

    public static String encrypt_AES(String str, String encryptionKey)
    {
        try
        {
            setKey_AES(encryptionKey);
            Cipher ciper = Cipher.getInstance("AES/ECB/PKCS5Padding");
            ciper.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(ciper.doFinal(str.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            System.out.println("Encryption Error AES");
            return null;
        }
    }

    public static String decrypt_AES(String str, String decryptionKey)
    {
        try
        {
            setKey_AES(decryptionKey);
            Cipher ciper = Cipher.getInstance("AES/ECB/PKCS5Padding");
            ciper.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(ciper.doFinal(Base64.getDecoder().decode(str)));
        }
        catch (Exception e)
        {
            System.out.println("Decryption Error AES");
            return null;
        }
    }

    /*
    public static void main(String[] args)
    {
        final String key = "ABCD";
        String str = "Hiiiiiiiiii";

        String enc = Symmetric_AES.encrypt_AES(str, key);
        String dec = Symmetric_AES.decrypt_AES(enc, key);

        System.out.println(enc);
        System.out.println(dec);

    }
    */
}