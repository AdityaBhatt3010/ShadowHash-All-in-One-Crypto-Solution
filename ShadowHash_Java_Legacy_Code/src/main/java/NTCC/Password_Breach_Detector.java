package NTCC;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import NTCC.Hash_SHA1;

public class Password_Breach_Detector
{
    private static String url = "https://api.pwnedpasswords.com/range/";

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Type password to check: ");
        String password = scanner.nextLine();

        int count = pwnedApiCheck(password);
        if (count > 0)
        {
            System.out.printf("%s was found %d times%n", password, count);
        }
        else
        {
            System.out.printf("%s was not found%n", password);
        }
    }

    private static int pwnedApiCheck(String password)
    {
        String sha1Password = Hash_SHA1.sha1Hash(password).toUpperCase();
        String first5Chars = sha1Password.substring(0, 5);
        String tail = sha1Password.substring(5);
        String url_final = url + first5Chars;
        //System.out.println(url_final);

        try
        {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url_final).build();

            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();

            String[] lines = responseBody.split("\\r?\\n");
            for (String line : lines)
            {
                String[] parts = line.split(":");
                if (parts.length == 2 && parts[0].equals(tail))
                {
                    return Integer.parseInt(parts[1]);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }
}
