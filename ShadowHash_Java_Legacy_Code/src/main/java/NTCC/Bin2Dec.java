package NTCC;

public class Bin2Dec
{
    // Method to convert binary array to decimal
    public static int binaryToDecimal(int[] arr)
    {
        int k = 7; // Initialize k to 7 (assuming 8-bit binary numbers)
        int m = 0; // Initialize m to 0, which will store the decimal value

        // Loop through each bit in the binary array
        for(int i: arr)
        {
            m += (int) i * Math.pow(2, k);
            //System.out.println(i + " " + (k)); // Debugging Statement
            k--;
        }

        return m;
    }
}
