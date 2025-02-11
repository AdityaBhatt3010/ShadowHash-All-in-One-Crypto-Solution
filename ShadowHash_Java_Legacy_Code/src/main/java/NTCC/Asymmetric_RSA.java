package NTCC;

import java.security.*;
import java.util.Base64;
import javax.crypto.Cipher;

public class Asymmetric_RSA
{
    public static KeyPair setKey_RSA()
    {
        try
        {
            // Generate a key pair for RSA
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048); // Set the key size to 2048 bits
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            return keyPair;
        }
        catch (NoSuchAlgorithmException e)
        {
            System.err.println("RSA Algorithm Not Found");
            e.printStackTrace();
            return new KeyPair(null, null);
        }
        catch (Exception e)
        {
            System.err.println("Error encrypting/decrypting message");
            e.printStackTrace();
            return new KeyPair(null, null);
        }
    }

    public static byte[] encrypt_RSA(String str, PublicKey publicKey)
    {
        try
        {
            // Create a Cipher object for encryption and decryption
            Cipher cipher = Cipher.getInstance("RSA");

            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedMessage = cipher.doFinal(str.getBytes());

            return encryptedMessage;
        }
        catch (Exception e)
        {
            System.err.println("Error encrypting message");
            e.printStackTrace();
            return null;
        }
    }

    public static String decrypt_RSA(byte[] str, PrivateKey privateKey)
    {
        try
        {
            // Create a Cipher object for encryption and decryption
            Cipher cipher = Cipher.getInstance("RSA");

            // Decrypt the message using the private key
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedMessage = cipher.doFinal(str);
            String decryptedMessageString = new String(decryptedMessage);

            return decryptedMessageString;
        }
        catch (Exception e)
        {
            System.err.println("Error encrypting message");
            e.printStackTrace();
            return "";
        }
    }


//    public static void main(String[] args)
//    {
//        String str = "Hiiiiiiiiii";
//
//        KeyPair keyPair = Asymmetric_RSA.setKey_RSA();
//
//        byte[] keyBytes = keyPair.getPublic().getEncoded();
//        System.out.println(Base64.getEncoder().encodeToString(keyBytes));
//        byte[] keyBytes1 = keyPair.getPrivate().getEncoded();
//        System.out.println(Base64.getEncoder().encodeToString(keyBytes1));
//
//        byte[] enc = Asymmetric_RSA.encrypt_RSA(str, keyPair.getPublic());
//        String dec = Asymmetric_RSA.decrypt_RSA(enc, keyPair.getPrivate());
//
//        System.out.println("Original text: " + str);
//        System.out.println("Encrypted Text: " + enc);
//        System.out.println("Decrypted Text: " + dec);
//    }
}
