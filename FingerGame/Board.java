import java.util.ArrayList;

/**
 * All of the hands in the game
 * 
 * @author Hoelzel
 * @version 4/7/2016
 */

public class Board
{
    private Hand[] p1Hands;
    private Hand[] p2Hands;
    private boolean isP1Turn;
    
    public Board()
    {
        p1Hands = new Hand[2];
        p2Hands = new Hand[2];
        
        for (int i = 0; i < 2; i ++)
        {
            p1Hands[i] = new Hand();
            p2Hands[i] = new Hand();
        }
        
        isP1Turn = true;
    }
    
    public Board(int n1, int n2, int n3, int n4, boolean isP1Turn)
    {
        p1Hands = new Hand[2];
        p2Hands = new Hand[2];
        
        p1Hands[0] = new Hand(n1);
        p1Hands[1] = new Hand(n2);
        p2Hands[0] = new Hand(n3);
        p2Hands[1] = new Hand(n4);
        
        this.isP1Turn = isP1Turn;
    }
    
    public void move(Move m)
    {
        if (isP1Turn)
        {
            if (m.isAHit()) p1Hands[m.getFrom()].hit(p2Hands[m.getNum2()]);
            else p1Hands[m.getFrom()].pass(p1Hands[1-m.getFrom()], m.getNum2());
        }
        else
        {
            if (m.isAHit()) p2Hands[m.getFrom()].hit(p1Hands[m.getNum2()]);
            else p2Hands[m.getFrom()].pass(p2Hands[1-m.getFrom()], m.getNum2());
        }
    }
    
    public String toString()
    {
        String temp = "";
        if (isP1Turn) temp += "P1 Turn";
        else temp += "P2 Turn";
        temp += "\nP2:  ";
        temp += p2Hands[0].getFingers();
        temp += "   ";
        temp += p2Hands[1].getFingers();
        temp += "\nP1:  ";
        temp += p1Hands[0].getFingers();
        temp += "   ";
        temp += p1Hands[1].getFingers();
        
        return temp;
    }
    
    public void switchTurns()
    {
        isP1Turn = !isP1Turn;
    }
    
    public boolean isP1Turn()
    {
        return isP1Turn;
    }
    
    public boolean isValidMove(Move m)
    {
        if (m.getFrom() < 0 || m.getFrom() > 1 ||m.getNum2() < 0 || m.getNum2() > Hand.MAX_FINGERS - 1) return false;
        else if (isP1Turn)
        {
            if (p1Hands[m.getFrom()].getFingers() == 0) return false;
            else if (m.isAHit())
            {
                if (m.getNum2() > 1 || p2Hands[m.getNum2()].getFingers() == 0) return false;
                else return true;
            }
            else
            {
                if (m.getNum2() < 1  || m.getNum2() > p1Hands[m.getFrom()].getFingers()) return false;
                else if (p1Hands[m.getFrom()].getFingers() == (p1Hands[1-m.getFrom()].getFingers() + m.getNum2())) return false;
                else return true;
            }
        }
        else
        {
            if (p2Hands[m.getFrom()].getFingers() == 0) return false;
            else if (m.isAHit())
            {
                if (m.getNum2() > 1 || p1Hands[m.getNum2()].getFingers() == 0) return false;
                else return true;
            }
            else
            {
                if (m.getNum2() < 1 || m.getNum2() > p2Hands[m.getFrom()].getFingers()) return false;
                else if (p2Hands[m.getFrom()].getFingers() == (p2Hands[1-m.getFrom()].getFingers() + m.getNum2())) return false;
                else return true;
            }
        }
    }
    
    public boolean isP1Winner()
    {
        return (p2Hands[0].getFingers() == 0 && p2Hands[1].getFingers() == 0);
    }
    
    public boolean isP2Winner()
    {
        return (p1Hands[0].getFingers() == 0 && p1Hands[1].getFingers() == 0);
    }
    
    public boolean basicallyEquals(Board other)
    {
        if (isP1Turn != other.isP1Turn) return false;
        
        boolean p1HandsSame = ((p1Hands[0].equals(other.p1Hands[0]) && p1Hands[1].equals(other.p1Hands[1])) || (p1Hands[0].equals(other.p1Hands[1]) && p1Hands[1].equals(other.p1Hands[0])));
        boolean p2HandsSame = ((p2Hands[0].equals(other.p2Hands[0]) && p2Hands[1].equals(other.p2Hands[1])) || (p2Hands[0].equals(other.p2Hands[1]) && p2Hands[1].equals(other.p2Hands[0])));
        return (p1HandsSame && p2HandsSame);
    }
    
    public ArrayList<Move> getAllValidMoves()
    {
        ArrayList<Move> moves = new ArrayList<Move>();
        
        if (isP1Winner() || isP2Winner()) return moves;
        
        for (int i = 0; i < 2; i ++)
        {
            for (int j = 0; j < 2; j ++)
            {
                Move m = new Move(true, i, j);
                if (isValidMove(m)) moves.add(m);
            }
            
            int num = 0;
            if (isP1Turn) num = p1Hands[i].getFingers();
            else num = p2Hands[i].getFingers();
            
            for (int j = 1; j <= num; j ++)
            {
                Move m = new Move(false, i, j);
                if (isValidMove(m)) moves.add(m);
            }
        }
        
        return moves;
    }
    
    public Board clone()
    {
        return new Board(p1Hands[0].getFingers(), p1Hands[1].getFingers(), p2Hands[0].getFingers(), p2Hands[1].getFingers(), isP1Turn);
    }
}
