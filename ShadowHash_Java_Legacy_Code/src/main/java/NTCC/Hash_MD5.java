package NTCC;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash_MD5
{
    public static String md5Hash(String input)
    {
        try
        {
            MessageDigest digest = MessageDigest.getInstance("MD5");
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
        System.out.println(md5Hash(str));
    }
    */
}