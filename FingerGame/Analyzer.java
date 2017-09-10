import java.util.ArrayList;

/**
 * analyzes the stuff
 * 
 * @author Hoelzel
 * @version 4/7/2016
 */

public class Analyzer
{
    private ArrayList<BoardState> boards;
    private boolean isP1;
    
    public Analyzer(boolean isPlayer1)
    {
        isP1 = isPlayer1;
        
        boards = new ArrayList<BoardState>();
        
        for (int n1 = 0; n1 <= Hand.MAX_FINGERS; n1 ++)
        {
            for (int n2 = n1; n2 <= Hand.MAX_FINGERS; n2 ++)
            {
                for (int n3 = 0; n3 <= Hand.MAX_FINGERS; n3 ++)
                {
                    for (int n4 = n3; n4 <= Hand.MAX_FINGERS; n4 ++)
                    {
                        boards.add(new BoardState(n1, n2, n3, n4, true));
                        boards.add(new BoardState(n1, n2, n3, n4, false));
                    }
                }
            }
        }
        
        for (BoardState b : boards)
        {
            ArrayList<Move> moves = b.getAllValidMoves();
            ArrayList<Board> temps = new ArrayList<Board>();
            
            if (moves.size() > 0)
            {
                for (Move m : moves)
                {
                    Board temp = b.clone();
                    temp.move(m);
                    temp.switchTurns();
                    temps.add(temp);
                }
                
                if (temps.size() > 1)
                {
                    for (int i = temps.size() - 1; i >= 0; i --)
                    {
                        for (int j = i - 1; j >= 0; j --)
                        {
                            if (temps.get(i).basicallyEquals(temps.get(j)))
                            {
                                temps.remove(i);
                                break;
                            }
                        }
                    }
                }
                
                for (Board temp : temps)
                {
                    for (BoardState b2 : boards)
                    {
                        if (temp.basicallyEquals(b2))
                        {
                            b.addFutureBoard(b2);
                            b2.addPastBoard(b);
                        }
                    }
                }
            }
        }
        
        int size = 0;
        
        while(boards.size() != size)
        {
            size = boards.size();
            
            for (BoardState b : boards)
            {
                ArrayList<BoardState> past = b.getPastBoards();
                
                if (past.size() > 1)
                {
                    for (int i = past.size() - 1; i >= 0; i --)
                    {
                        if (!boards.contains(past.get(i))) b.removePastBoard(past.get(i));
                    }
                }
            }
            
            for (int i = boards.size() - 1; i >= 0; i --)
            {
                if (boards.get(i).getPastBoards().size() == 0) boards.remove(i);
            }
        }
        
        int numValued;
        
        do
        {
            numValued = 0;
            
            for (BoardState b : boards)
            {
                if (b.getValue() == 0) 
                {
                    b.findValue();
                    if (b.getValue() != 0) numValued ++;
                }
            }
        }
        while (numValued > 0);
    }
    
    public Move getMove(Board current)
    {
        BoardState state = new BoardState(0, 0, 0, 0, false);
        
        for (BoardState b : boards)
        {
            if (b.basicallyEquals(current))
            {
                state = b;
                break;
            }
        }
        
        ArrayList<BoardState> future = state.getFutureBoards();
        
        ArrayList<BoardState> betterMoves = new ArrayList<BoardState>();
        
        if (isP1)
        {
            int max = -1;
            
            for (BoardState b : future)
            {
                if (b.getValue() > max)
                {
                    max = b.getValue();
                    betterMoves.clear();
                    betterMoves.add(b);
                }
                else if (b.getValue() == max) betterMoves.add(b);
            }
        }
        else
        {
            int min = 1;
            
            for (BoardState b : future)
            {
                if (b.getValue() < min)
                {
                    min = b.getValue();
                    betterMoves.clear();
                    betterMoves.add(b);
                }
                else if (b.getValue() == min) betterMoves.add(b);
            }
        }
        
        boolean willWin = ((isP1 && betterMoves.get(0).getValue() == 1) || (!isP1 && betterMoves.get(0).getValue() == -1));
        ArrayList<BoardState> timedBoards = new ArrayList<BoardState>();
        
        if (willWin)
        {
            int minDistance = 1000;
            
            for (BoardState b : betterMoves)
            {
                if (b.getDistanceToEnd() < minDistance)
                {
                    timedBoards.clear();
                    timedBoards.add(b);
                    minDistance = b.getDistanceToEnd();
                }
                else if (b.getDistanceToEnd() == minDistance) timedBoards.add(b);
            }
        }
        else
        {
            int maxDistance = -1;
            
            for (BoardState b : betterMoves)
            {
                if (b.getDistanceToEnd() > maxDistance)
                {
                    timedBoards.clear();
                    timedBoards.add(b);
                    maxDistance = b.getDistanceToEnd();
                }
                else if (b.getDistanceToEnd() == maxDistance) timedBoards.add(b);
            }
        }
        
        BoardState next = timedBoards.get((int)(Math.random() * timedBoards.size()));
        
        ArrayList<Move> moves = current.getAllValidMoves();
        
        for (Move m : moves)
        {
            Board temp = current.clone();
            temp.move(m);
            temp.switchTurns();
            if (temp.basicallyEquals(next)) return m;
        }
        
        return new Move(false, -1, -1);
    }
}
