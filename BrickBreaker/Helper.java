/**
 * Helpful static methods
 * 
 * @author Hoelzel
 * @version 10/18/2015
 */

public class Helper
{
    public static boolean approxEquals(double num1, double num2)
    {
        return Math.abs(num1 - num2) < 4;
    }
}
