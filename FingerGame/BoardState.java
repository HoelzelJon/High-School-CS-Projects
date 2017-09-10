import java.util.ArrayList;

/**
 * Keeps track of the other board states it is connected to
 * 
 * @author Hoelzel
 * @version 4/7/2016
 */

public class BoardState extends Board
{
    private ArrayList<BoardState> pastBoards;
    private ArrayList<BoardState> futureBoards;
    private int value;
    private int distanceToEnd;
    
    public BoardState(int n1, int n2, int n3, int n4, boolean b)
    {
        super(n1, n2, n3, n4, b);
        
        pastBoards = new ArrayList<BoardState>();
        futureBoards = new ArrayList<BoardState>();
        if (isP1Winner()) 
        {
            value = 1;
            distanceToEnd = 0;
        }
        else if (isP2Winner())
        {
            value = -1;
            distanceToEnd = 0;
        }
        else
        {
            value = 0;
            distanceToEnd = -1;
        }
    }
    
    public void addPastBoard(BoardState b)
    {
        pastBoards.add(b);
    }
    
    public void addFutureBoard(BoardState b)
    {
        futureBoards.add(b);
    }
    
    public ArrayList<BoardState> getPastBoards()
    {
        return pastBoards;
    }
    
    public ArrayList<BoardState> getFutureBoards()
    {
        return futureBoards;
    }
    
    public void removePastBoard(BoardState p)
    {
        pastBoards.remove(p);
    }
    
    public int getValue()
    {
        return value;
    }
    
    public void findValue()
    {
        if (value != 0) return;
        else if (isP1Turn())
        {
            int max = -1;
            
            for (BoardState b : futureBoards)
            {
                if (b.getValue() > max)
                {
                    max = b.value;
                }
            }
            
            value = max;
        }
        else
        {
            int min = 1;
            
            for (BoardState b : futureBoards)
            {
                if (b.getValue() < min)
                {
                    min = b.value;
                }
            }
            
            value = min;
        }
        
        if (value != 0)
        {
            boolean willWin = ((isP1Turn() && value == 1) || (!isP1Turn() && value == -1));
            
            if (willWin)
            {
                int minDistance = 1000;
                
                for (BoardState b : futureBoards)
                {
                    if (b.value == value && b.distanceToEnd < minDistance) minDistance = b.distanceToEnd;
                }
                
                distanceToEnd = minDistance + 1;
            }
            else
            {
                int maxDistance = -1;
                
                for (BoardState b : futureBoards)
                {
                    if (b.value == value && b.distanceToEnd > maxDistance) maxDistance = b.distanceToEnd;
                }
                
                distanceToEnd = maxDistance + 1;
            }
        }
    }
    
    public int getDistanceToEnd()
    {
        return distanceToEnd;
    }
}
