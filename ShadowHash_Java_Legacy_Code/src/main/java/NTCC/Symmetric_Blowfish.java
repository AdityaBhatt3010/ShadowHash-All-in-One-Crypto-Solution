package NTCC;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

public class Symmetric_Blowfish
{
    private static SecretKeySpec secretKey;
    private static byte[] key;

    public static void setKey_Blowfish(String myKey)
    {
        try
        {
            key = myKey.getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16); // Blowfish accepts key sizes from 32 to 448 bits, here we choose 128 bits

            secretKey = new SecretKeySpec(key, "Blowfish");
            //System.out.println("Key: " + secretKey);
        }
        catch (Exception e)
        {
            System.out.println("Hashing Error Blowfish");
        }
    }

    public static String encrypt_Blowfish(String str, String encryptionKey)
    {
        try
        {
            setKey_Blowfish(encryptionKey);
            Cipher cipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            System.out.println("Encryption Error Blowfish");
            return null;
        }
    }

    public static String decrypt_Blowfish(String str, String decryptionKey)
    {
        try
        {
            setKey_Blowfish(decryptionKey);
            Cipher cipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(str)));
        }
        catch (Exception e)
        {
            System.out.println("Decryption Error Blowfish");
            return null;
        }
    }

    /*
    public static void main(String[] args)
    {
        String original = "Hello, World!";
        String key = "this_is_a_secret_key";
        String encrypted = encrypt_Blowfish(original, key);
        String decrypted = decrypt_Blowfish(encrypted, key);

        System.out.println("Original: " + original);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
    }
    */
}
