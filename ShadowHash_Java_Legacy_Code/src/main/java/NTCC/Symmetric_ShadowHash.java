package NTCC;

import java.io.*;
import java.util.Random;
import java.util.Scanner;
import NTCC.Dec2Bin;
import NTCC.Bin2Dec;
import NTCC.Symmetric_AES;
import NTCC.Symmetric_3DES;
import NTCC.Symmetric_Blowfish;


public class Symmetric_ShadowHash
{
    private static String Id1 = "";

    public static String encrypt_L1(String str)
    {
        int[][] arr = new int[str.length()][8];
        String[] key1 = new String[str.length()];
        String[] key2 = new String[str.length()];
        String[] key3 = new String[str.length()];
        String[] key4 = new String[str.length()];
        String[] key5 = new String[str.length()];
        StringBuilder encryptedString = new StringBuilder();
        StringBuilder keys = new StringBuilder(); // Store keys along with encrypted string
        int count = 0;

        StringBuilder k6 = new StringBuilder();

        for(int i = 0; i < Id1.length(); i += 2)
        {
            k6.append(Id1.toCharArray()[i]);
        }

        for (char c : str.toCharArray())
        {
            int[] bin = Dec2Bin.decimalToBinary((int) c);

            for (int i = 0; i < 8; i++)
            {
                arr[count][i] = bin[i];
            }
            count++;
        }

        for (int i = 0; i < str.length(); i++)
        {
            Random r = new Random();
            int k1 = r.nextInt(3, 9);
            int k2 = r.nextInt(0, 3);
            int k3 = r.nextInt(0, 3);
            int k4 = r.nextInt(0, 2147483647);
            int k5 = r.nextInt(0, 2147483647);

            for (int j = 0; j < 8; j++)
            {
                if (j <= k1)
                    continue;
                else
                {
                    switch (arr[i][j])
                    {
                        case 0:
                            arr[i][j] = 1;
                            break;
                        case 1:
                            arr[i][j] = 0;
                            break;
                    }
                }
            }

            key1[i] = String.valueOf(Integer.rotateLeft(k1 ,k2));
            key2[i] = String.valueOf(Integer.rotateRight(k2, k3));
            key3[i] = Symmetric_3DES.encrypt_3DES(String.valueOf(k3), String.valueOf(k4));
            key4[i] = Symmetric_AES.encrypt_AES(String.valueOf(k4), String.valueOf(k5));
            key5[i] = Symmetric_Blowfish.encrypt_Blowfish(String.valueOf(k5), String.valueOf(k6));

            keys.append(key1[i]);
            keys.append("|");
            keys.append(key2[i]);
            keys.append("|");
            keys.append(key3[i]);
            keys.append("|");
            keys.append(key4[i]);
            keys.append("|");
            keys.append(key5[i]);
            keys.append(" ");
        }

        for (int i = 0; i < str.length(); i++)
        {
            encryptedString.append((char) Bin2Dec.binaryToDecimal(arr[i]));
//            encryptedString.append(key1[i]);
//            encryptedString.append(key2[i]);
//            encryptedString.append(key3[i]);
//            encryptedString.append(key4[i]);
        }

        try
        {
            FileWriter Writer = new FileWriter("keyFile.txt");
            Writer.write(keys.toString());
            Writer.close();
        }
        catch (IOException e)
        {
            System.out.println("An error has occurred.");
            e.printStackTrace();
        }

        return encryptedString.toString();
    }


    public static String decrypt_L1(String encryptedData)
    {
        StringBuilder key = new StringBuilder();

        StringBuilder k6 = new StringBuilder();

        for(int i = 0; i < Id1.length(); i += 2)
        {
            k6.append(Id1.toCharArray()[i]);
        }

        try
        {
            File Obj = new File("keyFile.txt");
            Scanner Reader = new Scanner(Obj);
            while (Reader.hasNextLine())
            {
                key.append(Reader.nextLine());
            }
            Reader.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("An error has occurred.");
            e.printStackTrace();
        }

        String[] keyLines = key.toString().split(" ");

        StringBuilder decryptedString = new StringBuilder();

//        System.out.println(encryptedData);
//        for (int i = 0; i < encryptedData.length(); i++)
//        {
//            System.out.println(keyLines[i]);
//        }


        for (int i = 0; i < encryptedData.length(); i++)
        {
            char encryptedChar = encryptedData.charAt(i);

            String[] keyParts = keyLines[i].split("\\|");

            // Reverse the bitwise operations correctly
            int k5 = Integer.parseInt(Symmetric_Blowfish.decrypt_Blowfish(keyParts[4], String.valueOf(k6)));
            int k4 = Integer.parseInt(Symmetric_AES.decrypt_AES(keyParts[3], String.valueOf(k5)));
            int k3 = Integer.parseInt(Symmetric_3DES.decrypt_3DES(keyParts[2], String.valueOf(k4)));
            // Apply the reverse operations to get the original k1 and k2 values
            int k2 = Integer.rotateLeft(Integer.parseInt(keyParts[1]), k3); // Reverse the right shift
            int k1 = Integer.rotateRight(Integer.parseInt(keyParts[0]), k2); // Reverse the left shift


            int[] bin = Dec2Bin.decimalToBinary((int) encryptedChar);

            for (int j = 0; j < 8; j++)
            {
                if (j <= k1)
                {
                    continue;
                } else
                {
                    switch (bin[j])
                    {
                        case 0:
                            bin[j] = 1;
                            break;
                        case 1:
                            bin[j] = 0;
                            break;
                    }
                }
            }

            decryptedString.append((char) Bin2Dec.binaryToDecimal(bin));

        }

        return decryptedString.toString();
    }

    /*
    public static void main(String[] args)
    {
        Id1 = "Adi3010";
        String og = "Aditya Bhatt";
        String enc = encrypt_L1(og);
        String dec = decrypt_L1(enc);

        System.out.println(og);
        System.out.println(enc);
        System.out.println(dec);
    }
    */
}
