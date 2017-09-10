import java.util.Scanner;

/**
 * The user plays the game
 * 
 * @author Hoelzel
 * @version 4/6/2016
 */

public class MainGame
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        
        Board b = new Board();
        
        Analyzer a = new Analyzer(false);
        //Analyzer a2 = new Analyzer(false);
        
        while (!b.isP1Winner() && !b.isP2Winner())
        {
            System.out.println(b.toString());
            
            Move m = new Move(false, -1, -1);
            
            if (!b.isP1Turn())
            {
                m = a.getMove(b);
                
                try {Thread.sleep(1000);}
                catch(Exception e) {};
            }
            else
            {
                //m = a2.getMove(b);
                
                
                
                boolean hasMadeValidMove = false;
                
                while (!hasMadeValidMove)
                {
                    
                    
                    System.out.print("Hit or pass (h/p)? ");
                    boolean isAHit = false;
                    String s = in.nextLine();
                    if (s.equals("h")) isAHit = true;
                    
                    System.out.print("Which hand? ");
                    int from = in.nextInt();
                    in.nextLine();
                    
                    if (isAHit) System.out.print("Which hand to hit? ");
                    else System.out.print("# to pass: ");
                    int num2 = in.nextInt();
                    in.nextLine();
                    
                    m = new Move(isAHit, from ,num2);
                    
                    if (b.isValidMove(m)) hasMadeValidMove = true;
                    else
                    {
                        System.out.println("Invalid move");
                        System.out.println(b.toString());
                    }
                }
            }
            
            b.move(m);
            System.out.println();
            b.switchTurns();
        }
        
        System.out.println(b.toString());
        
        if (b.isP1Winner()) System.out.println("P1 wins");
        else System.out.println("P2 wins");
    }
}
