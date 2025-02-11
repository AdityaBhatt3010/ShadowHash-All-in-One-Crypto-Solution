package NTCC;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Password_Generator_UserInput
{

    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()_+[]{}|;:,.<>?";
    private static final Random RANDOM = new Random();

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter your mother's name: ");
        String motherName = scanner.nextLine().trim();

        System.out.print("Enter your father's name: ");
        String fatherName = scanner.nextLine().trim();

        System.out.print("Enter your spouse's name: ");
        String spouseName = scanner.nextLine().trim();

        System.out.print("Enter your pet's name: ");
        String petName = scanner.nextLine().trim();

        System.out.print("Enter your address: ");
        String address = scanner.nextLine().trim();

        System.out.print("Enter your date of birth (dd/mm/yyyy): ");
        String dob = scanner.nextLine().trim();

        System.out.print("Enter some special keywords (comma separated): ");
        String keywordsInput = scanner.nextLine().trim();
        String[] keywords = keywordsInput.split(",");

        List<String> userDetails = new ArrayList<>();
        if (!name.isEmpty()) userDetails.add(name);
        if (!motherName.isEmpty()) userDetails.add(motherName);
        if (!fatherName.isEmpty()) userDetails.add(fatherName);
        if (!spouseName.isEmpty()) userDetails.add(spouseName);
        if (!petName.isEmpty()) userDetails.add(petName);
        if (!address.isEmpty()) userDetails.add(address);
        if (!dob.isEmpty())
        {
            // Validate and format DOB
            String formattedDob = formatDob(dob);
            if (formattedDob != null)
            {
                userDetails.add(formattedDob);
            }
            else
            {
                System.out.println("Invalid Date of Birth format. Skipping DOB.");
            }
        }
        for (String keyword : keywords)
        {
            if (!keyword.trim().isEmpty())
            {
                userDetails.add(keyword.trim());
            }
        }

        if (userDetails.isEmpty())
        {
            System.out.println("No input provided. Cannot generate a password.");
        }
        else
        {
            String password = generatePassword(userDetails);
            System.out.println("Generated Password: " + password);
        }
    }

    private static String formatDob(String dob)
    {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMddyyyy");
        try
        {
            Date date = inputFormat.parse(dob);
            return outputFormat.format(date);
        }
        catch (ParseException e)
        {
            return null;
        }
    }

    private static String generatePassword(List<String> userDetails)
    {
        StringBuilder password = new StringBuilder();
        boolean hasUpper = false, hasLower = false, hasDigit = false, hasSpecial = false;

        while (password.length() < 8)
        {
            String detail = userDetails.get(RANDOM.nextInt(userDetails.size()));
            String transformedDetail = applyRandomTransformations(detail);

            for (char c : transformedDetail.toCharArray())
            {
                if (Character.isUpperCase(c)) hasUpper = true;
                if (Character.isLowerCase(c)) hasLower = true;
                if (Character.isDigit(c)) hasDigit = true;
                if (SPECIAL_CHARACTERS.indexOf(c) != -1) hasSpecial = true;
            }

            password.append(transformedDetail);
        }

        if (password.length() > 20)
        {
            password.setLength(20);
        }

        if (!hasUpper || !hasLower || !hasDigit || !hasSpecial)
        {
            return generatePassword(userDetails); // Regenerate if any criteria are missing
        }

        return password.toString();
    }

    private static String applyRandomTransformations(String input)
    {
        StringBuilder transformed = new StringBuilder();
        for (char c : input.toCharArray()) {
            switch (Character.toLowerCase(c)) {
                case 'a':
                    transformed.append(RANDOM.nextBoolean() ? '@' : 'A');
                    break;
                case 's':
                    transformed.append(RANDOM.nextBoolean() ? '$' : 'S');
                    break;
                case 'o':
                    transformed.append(RANDOM.nextBoolean() ? '0' : 'O');
                    break;
                case 'i':
                    transformed.append(RANDOM.nextBoolean() ? 'l' : 'I');
                    break;
                case 'e':
                    transformed.append(RANDOM.nextBoolean() ? '3' : 'E');
                    break;
                default:
                    if (Character.isLetter(c))
                    {
                        transformed.append(RANDOM.nextBoolean() ? Character.toUpperCase(c) : Character.toLowerCase(c));
                    }
                    else if (Character.isDigit(c))
                    {
                        transformed.append(c);
                    }
                    else
                    {
                        transformed.append(SPECIAL_CHARACTERS.charAt(RANDOM.nextInt(SPECIAL_CHARACTERS.length())));
                    }
                    break;
            }
        }

        return transformed.toString();
    }
}