import java.util.ArrayList;
import java.util.Scanner;

/**
 * Some static methods to help with chess
 * 
 * @author Hoelzel
 * @version 4/2/2015
 */

public class Helper
{
    public static String toLetter(int num)
    {
        if (num == 0) return "A";
        else if (num == 1) return "B";
        else if (num == 2) return "C";
        else if (num == 3) return "D";
        else if (num == 4) return "E";
        else if (num == 5) return "F";
        else if (num == 6) return "G";
        else if (num == 7) return "H";
        else return "ERROR";
    }
    
    public static String toSymbol(Piece p)
    {
        if (p instanceof BlankSpace) return "";
        else if (p instanceof Pawn)
        {
            if (p.getTeam().equals(Team.PLAYER1)) return "\u2659";
            else return "\u265F";
        }
        else if (p instanceof Rook)
        {
            if (p.getTeam().equals(Team.PLAYER1)) return "\u2656";
            else return "\u265C";
        }
        else if (p instanceof Bishop)
        {
            if (p.getTeam().equals(Team.PLAYER1)) return "\u2657";
            else return "\u265D";
        }
        else if (p instanceof King)
        {
            if (p.getTeam().equals(Team.PLAYER1)) return "\u2654";
            else return "\u265A";
        }
        else if (p instanceof Queen)
        {
            if (p.getTeam().equals(Team.PLAYER1)) return "\u2655";
            else return "\u265B";
        }
        else if (p instanceof Knight)
        {
            if (p.getTeam().equals(Team.PLAYER1)) return "\u2658";
            else return "\u265E";
        }
        else return "ERROR";
    }
    
    public static boolean isValidCode(String code)
    {
        if (getX(code) == -1 || getY(code) == -1) return false;
        else return true;
    }
    
    public static int getX(String code)
    {
        if (code.length() == 2)
        {
            String letter = code.substring(0, 1);
            
            if (letter.equalsIgnoreCase("A")) return 0;
            else if (letter.equalsIgnoreCase("B")) return 1;
            else if (letter.equalsIgnoreCase("C")) return 2;
            else if (letter.equalsIgnoreCase("D")) return 3;
            else if (letter.equalsIgnoreCase("E")) return 4;
            else if (letter.equalsIgnoreCase("F")) return 5;
            else if (letter.equalsIgnoreCase("G")) return 6;
            else if (letter.equalsIgnoreCase("H")) return 7;
            else return -1;
        }
        else return -1;
    }
    
    public static int getY(String code)
    {
        if (code.length() == 2)
        {
            String num = code.substring(1, 2);
            
            if (isAValidInt(num)) return Integer.parseInt(num) - 1;
            else return -1;
        }
        else return -1;
    }
    
    public static boolean isAValidInt(String s)
    {
        return (s.equals("1") || s.equals("2") || s.equals("3") || s.equals("4") || s.equals("5") || s.equals("6") || s.equals("7") || s.equals("8"));
    }
    
    /**
     * Helps for deciding if moves are valid in the Board class
     */
    public static ArrayList<OrderedPair> getAllSpotsBetween(Move m)
    {
        OrderedPair spot1 = m.getOldSpot();
        OrderedPair spot2 = m.getNewSpot();
        int xChange = spot2.getX() - spot1.getX();
        int yChange = spot2.getY() - spot1.getY();
        ArrayList<OrderedPair> temp = new ArrayList<OrderedPair>();
        
        if (spot1.getX() == spot2.getX())
        {
            if (spot1.getY() > spot2.getY())
            {
                for (int i = spot1.getY() - 1; i > spot2.getY(); i --)
                {
                    temp.add(new OrderedPair(spot1.getX(), i));
                }
            }
            else
            {
                for (int i = spot1.getY() + 1; i < spot2.getY(); i ++)
                {
                    temp.add(new OrderedPair(spot1.getX(), i));
                }
            }
        }
        else if (spot1.getY() == spot2.getY())
        {
            if (spot1.getX() > spot2.getX())
            {
                for (int i = spot1.getX() - 1; i > spot2.getX(); i --)
                {
                    temp.add(new OrderedPair(i, spot1.getY()));
                }
            }
            else
            {
                for (int i = spot1.getX() + 1; i < spot2.getX(); i ++)
                {
                    temp.add(new OrderedPair(i, spot1.getY()));
                }
            }
        }
        else if (Math.abs(xChange) == Math.abs(yChange)) // diagonal
        {
            if (yChange > 0 && xChange < 0)
            {
                for (int i = spot1.getX() - 1; i > spot2.getX(); i --)
                {
                    temp.add(new OrderedPair(i, spot1.getY() + spot1.getX() - i));
                }
            }
            else if (yChange < 0 && xChange < 0)
            {
                for (int i = spot1.getX() - 1; i > spot2.getX(); i --)
                {
                    temp.add(new OrderedPair(i, spot1.getY() - spot1.getX() + i));
                }
            }
            else if (yChange > 0 && xChange > 0)
            {
                for (int i = spot1.getX() + 1; i < spot2.getX(); i ++)
                {
                    temp.add(new OrderedPair(i, spot1.getY() + i - spot1.getX()));
                }
            }
            else if (yChange < 0 && xChange > 0)
            {
                for (int i = spot1.getX() + 1; i < spot2.getX(); i ++)
                {
                    temp.add(new OrderedPair(i, spot1.getY() - i + spot1.getX()));
                }
            }
            else System.out.println("Something went wrong in the helper class (getAllSpotsBetween method).");
        }
        
        return temp;
    }
    
    public static boolean isOppositeTeam(Team t1, Team t2)
    {
        if (t1.equals(Team.PLAYER1) && t2.equals(Team.PLAYER2)) return true;
        else if (t1.equals(Team.PLAYER2) && t2.equals(Team.PLAYER1)) return true;
        else return false;
    }
    
    public static Team getOppositeTeam(Team t)
    {
        if (t.equals(Team.PLAYER1)) return Team.PLAYER2;
        else if (t.equals(Team.PLAYER2)) return Team.PLAYER1;
        else return Team.NEITHER;
    }
    
    public static Piece getChosenPiece(Team t)
    {
        Scanner in = new Scanner(System.in);
        
        while (true)
        {
            System.out.print("Enter the type of piece to switch the pawn to\n(\"Q\" = \u2655, \"K\" = \u2658 , \"B\" = \u2657, \"R\" = \u2656): ");
            
            String input = in.nextLine();
            
            if (input.equalsIgnoreCase("q")) return new Queen(t);
            else if (input.equalsIgnoreCase("k")) return new Knight(t);
            else if (input.equalsIgnoreCase("b")) return new Bishop(t);
            else if (input.equalsIgnoreCase("r"))
            {
                Rook r = new Rook(t);
                r.justMoved();
                return r;
            }
            else System.out.println("Invalid entry.");
        }
    }
    
    public static String getCode(OrderedPair spot)
    {
        String temp = "";
        temp += toLetter(spot.getX());
        temp += "" + (spot.getY() + 1);
        return temp;
    }
    
    public static OrderedPair getBoardPosition(int x, int y, ChessComponent c)
    {
        int boardX = (x - c.getMargin()) / c.getSquare();
        int boardY = (y - c.getMargin()) / c.getSquare();
        return new OrderedPair(boardX, boardY);
    }
    
    public static boolean isWithinBoard(OrderedPair spot)
    {
        return (spot.getY() >= 0 && spot.getY() <= 7 && spot.getX() >= 0 && spot.getX() <= 7);
    }
    
    public static boolean approxEquals(double n1, double n2)
    {
        return (Math.abs(n1 - n2) <= .1);
    }
    
    /**
     * n1 = bigger(?) number (true if n1 > n2)
     */
    public static boolean isLarger(double n1, double n2)
    {
        return (n1 - n2) > .1;
    }
}
