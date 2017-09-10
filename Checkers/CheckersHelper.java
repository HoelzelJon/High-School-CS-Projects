/**
 * Make some static methods to help with checkers
 * 
 * @author Hoelzel
 * @version 3/27/2015
 */
public class CheckersHelper
{
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
    
    // called in getY (will always be a 1-character String)
    public static boolean isAValidInt(String s)
    {
        return (s.equals("1") || s.equals("2") || s.equals("3") || s.equals("4") || s.equals("5") || s.equals("6") || s.equals("7") || s.equals("8"));
    }
    
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
    
    public static String toCode(int x, int y)
    {
        String temp = "";
        temp += toLetter(x);
        temp += "" + (y + 1);
        return temp;
    }
    
    public static Team getOppositeTeam(Team t)
    {
        if (t.equals(Team.PLAYER1)) return Team.PLAYER2;
        else if (t.equals(Team.PLAYER2)) return Team.PLAYER1;
        else return null;
    }
    
    public static OrderedPair getBoardPosition(int x, int y)
    {
        int boardX = (x - CheckersComponent.MARGIN) / CheckersComponent.SQUARE;
        int boardY = (y - CheckersComponent.MARGIN) / CheckersComponent.SQUARE;
        return new OrderedPair(boardX, boardY);
    }
    
    public static boolean isWithinBoard(OrderedPair spot)
    {
        return (spot.getX() < 8 && spot.getY() < 8 && spot.getX() >= 0 && spot.getY() >= 0);
    }
}
