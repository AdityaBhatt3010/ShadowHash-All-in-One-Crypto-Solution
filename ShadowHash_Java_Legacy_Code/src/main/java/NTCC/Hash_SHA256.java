package NTCC;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash_SHA256
{
    public static String sha256Hash(String input)
    {
        try
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
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
        String str = "Aditya Bhatt";
        System.out.println(sha256Hash(str));
    }
    */
}