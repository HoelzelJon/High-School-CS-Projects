import java.util.Scanner;

/**
 * Test the times the drunk guy passes through the origin in different dimentions
 * 
 * @author Hoelzel
 * @version 11/21/2015
 */

public class DrunkTester1
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        
        System.out.print("Dimentions: ");
        int dimentions = in.nextInt();
        in.nextLine();
        
        System.out.print("Steps: ");
        int steps = in.nextInt();
        in.nextLine();
        
        System.out.print("Trials: ");
        int trials = in.nextInt();
        in.nextLine();
        
        
        int[] zeroes = new int[(steps / 2) + 1];
        
        for (int t = 0; t < trials; t ++)
        {
            Drunk d = new Drunk(dimentions);
            
            for (int s = 0; s <= steps; s ++)
            {
                if (d.isAtOrigin()) 
                {
                    //System.out.println(s);
                    zeroes[(s / 2)] ++;
                }
                d.wander();
            }
            
            //System.out.print("Ended at (");
            //for (int i = 0; i < dimentions; i ++)
            //{
            //    System.out.print(d.getPos(i));
            //    if (i != dimentions - 1) System.out.print(", ");
            //}
            //System.out.println(")");
        }
        
        for (int s = 0; s <= (steps / 2); s ++)
        {
            if (zeroes[s] > 0) System.out.println("Steps: " + (s * 2) + ", Hits: " + zeroes[s]);
        }
    }
}