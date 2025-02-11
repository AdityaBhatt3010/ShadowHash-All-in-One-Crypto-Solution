package NTCC;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Scanner;

public class Email_Breach_Detector
{
    final private static String url = "https:/hackcheck.woventeams.com/api/v4/breachedaccount/";

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Type Mail Id to check: ");
        String mail = scanner.nextLine();

        String url_final = url + mail;
        try
        {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url_final).build();

            Response httpResponse = client.newCall(request).execute();
            int k = httpResponse.code();

            if (k == 200)
            {
                System.out.println("Your Mail was found in Data Breach");
            }
            else if (k == 404)
            {
                System.out.println("You are Safe");
            }
            else
            {
                System.out.println(k);
            }
        }
        catch (Exception e)
        {
            System.out.println("Error " + e);
        }
    }
}
