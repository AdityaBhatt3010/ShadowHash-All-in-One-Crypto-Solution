package NTCC;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Password_Strength_Detector
{
    static int rating = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();


        // Parameter 1: Length (at least 8 characters)
        Thread lengthThread = new Thread(() ->
        {
            if (password.length() >= 8)
            {
                rating++;
                //System.out.println("Len");
            }
        });

        // Parameter 2: Uppercase letters
        Thread uppercaseThread = new Thread(() ->
        {
            boolean hasUppercase = false;
            for (char c : password.toCharArray())
            {
                if (Character.isUpperCase(c))
                {
                    hasUppercase = true;
                    break;
                }
            }
            if (hasUppercase)
            {
                rating++;
                //System.out.println("Upper");
            }
        });

        // Parameter 3: Lowercase letters
        Thread lowercaseThread = new Thread(() ->
        {
            boolean hasLowercase = false;
            for (char c : password.toCharArray())
            {
                if (Character.isLowerCase(c))
                {
                    hasLowercase = true;
                    break;
                }
            }
            if (hasLowercase)
            {
                rating++;
                //System.out.println("Lower");
            }
        });

        // Parameter 4: Digits
        Thread digitThread = new Thread(() ->
        {
            boolean hasDigit = false;
            for (char c : password.toCharArray())
            {
                if (Character.isDigit(c))
                {
                    hasDigit = true;
                    break;
                }
            }
            if (hasDigit)
            {
                rating++;
                //System.out.println("Digit");
            }
        });

        // Parameter 5: Special characters
        Thread splCharThread = new Thread(() ->
        {
            boolean hasSpecialChar = false;
            for (char c : password.toCharArray())
            {
                if (!Character.isLetterOrDigit(c))
                {
                    hasSpecialChar = true;
                    break;
                }
            }
            if (hasSpecialChar)
            {
                rating++;
                //System.out.println("Spl");
            }
        });

        // Parameter 6: No consecutive characters
        Thread consecutiveThread = new Thread(() ->
        {
            boolean hasConsecutiveChars = false;
            for (int i = 0; i < password.length() - 1; i++)
            {
                if (password.charAt(i) == password.charAt(i + 1))
                {
                    hasConsecutiveChars = true;
                    break;
                }
            }
            if (!hasConsecutiveChars)
            {
                rating++;
                //System.out.println("Cons");
            }
        });

        // Parameter 7: No common patterns (e.g. "qwerty", "123456")
        Thread rockYouThread = new Thread(() ->
        {
            ArrayList<String> commonPatterns = new ArrayList<String>();
            int i = 0;
            try
            {
                File obj = new File("C:\\Users\\infoa\\IdeaProjects\\AdityaBhatt_Java_I\\src\\NTCC\\rockyou.txt");
                Scanner Reader = new Scanner(obj);
                while (Reader.hasNextLine())
                {
                    commonPatterns.add(Reader.next());
                    i++;
                }
                Reader.close();
            }
            catch (FileNotFoundException e)
            {
                System.out.println("An error has occurred.");
                e.printStackTrace();
            }
            catch (Exception e)
            {
                System.out.print("");
            }

            boolean hasCommonPattern = false;
            for (String pattern : commonPatterns)
            {
                if (password.equals(pattern))
                {
                    hasCommonPattern = true;
                    break;
                }
            }
            if (!hasCommonPattern)
            {
                rating += 2;
                //System.out.println("Rock");
            }
        });

        // Parameter 8: No sequential characters (e.g. "abcd", "1234")
        Thread sequentialThread = new Thread(() ->
        {
            boolean hasSequentialChars = false;
            for (int i = 0; i < password.length() - 1; i++)
            {
                if (password.charAt(i) + 1 == password.charAt(i + 1))
                {
                    hasSequentialChars = true;
                    break;
                }
            }
            if (!hasSequentialChars)
            {
                rating += 2;
                //System.out.println("Seq");
            }
        });

        // Start all threads
        lengthThread.start();
        uppercaseThread.start();
        lowercaseThread.start();
        digitThread.start();
        splCharThread.start();
        consecutiveThread.start();
        rockYouThread.start();
        sequentialThread.start();

        // Wait for all threads to finish
        try
        {
            lengthThread.join();
            uppercaseThread.join();
            lowercaseThread.join();
            digitThread.join();
            splCharThread.join();
            consecutiveThread.join();
            rockYouThread.join();
            sequentialThread.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        System.out.println("Password rating: " + rating + "/10");
        if (rating >= 8)
        {
            System.out.println("Password is strong!");
        }
        else
        {
            System.out.println("Password is weak. Try to improve it!");
        }
    }
}