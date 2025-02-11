package NTCC;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import okhttp3.*;

public class Virus_Detection_Checker
{
    private static final String API_KEY = "4e4384731df1a03a46d83945ae7454fec28d5f5d4627d510461c264e1d24b5c9";
    private static final String VIRUSTOTAL_API_URL = "https://www.virustotal.com/vtapi/v2/file/scan";

    public static void scanFile(String filePath)
    {
        try
        {
            OkHttpClient client = new OkHttpClient();
            byte[] fileContent = Files.readAllBytes(Paths.get(filePath));
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("apikey", API_KEY)
                    .addFormDataPart("file", filePath,
                            RequestBody.create(MediaType.parse("application/octet-stream"), fileContent))
                    .build();

            Request request = new Request.Builder()
                    .url(VIRUSTOTAL_API_URL)
                    .post(requestBody)
                    .build();

            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();

            if (responseBody.contains("Virus detected"))
            {
                System.out.println("Virus Present");
            }
            else
            {
                System.out.println("File is Safe");
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void func(String filePath)
    {
        if (!Files.exists(Paths.get(filePath)))
        {
            System.err.println("File not found: " + filePath);
            return;
        }
        // Rest of your code for scanning the file
    }

    public static void main(String[] args)
    {
        func("C:\\Users\\infoa\\IdeaProjects\\Try\\src\\main\\java\\org\\example\\Hii.txt");
        scanFile("C:\\Users\\infoa\\IdeaProjects\\Try\\src\\main\\java\\org\\example\\Hii.txt");
    }


}
