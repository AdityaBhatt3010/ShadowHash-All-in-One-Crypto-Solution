package NTCC;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash_Try
{
    public static String tryHash(String input, String hash)
    {
        try
        {
            MessageDigest digest = MessageDigest.getInstance(hash);
            byte[] hashBytes = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes)
            {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
            return "";
        }
    }

    /*
    public static void main(String[] args)
    {
        String str = "Adi";
        String hash = "SHA-256";
        System.out.println(tryHash(str, hash));
    }
    */
}