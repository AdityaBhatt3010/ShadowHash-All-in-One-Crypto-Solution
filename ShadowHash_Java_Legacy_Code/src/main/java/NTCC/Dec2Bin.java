package NTCC;

public class Dec2Bin
{
    public static int[] decimalToBinary(int n)
    {
        int c = 0, j = 0;
        int[] arr_temp = new int[8];
        int[] arr_final = new int[8];
        while (n > 0)
        {
            arr_temp[c] = (int) n%2;
            n = (int) n/2;
            c++;
        }
        arr_temp[c++] = n;
        while (c<8)
        {
            arr_temp[c] = 0;
            c++;
        }

        for(int i: arr_temp)
        {
            arr_final[7-j] = arr_temp[j];
            j++;
        }

        return arr_final;
    }
}
