import java.util.ArrayList;

/**
 * A better computer player
 * 
 * @author Hoelzel
 * @version 5/2/2015
 */

public class CompLv3 extends ComputerPlayer
{
    public static final int TAKING_VALUE = 3;

    public CompLv3(Board b, Team t)
    {
        super.setTeam(t);
        super.setBoard(b);
    }
        
    public Move nextMove()
    {
        ArrayList<Move> bestMoves = getAllLikelyMoves();
        
        return bestMoves.get((int)(Math.random() * bestMoves.size()));
    }
    
    public Piece pieceToChangePawnTo()
    {
        return new Queen(getTeam());
    }
    
    public ArrayList<Move> getAllLikelyMoves()
    {
        ArrayList<Move> allMoves = getBoard().getAllNonCheckMoves(getTeam());
        
        ArrayList<Move> bestMoves = new ArrayList<Move>();
        int maxBoardValue = Integer.MIN_VALUE;
        for (Move m : allMoves)
        {
            Board tempBoard = getBoard().clone();
            tempBoard.movePiece(m);
            int value = tempBoard.getBoardValue(getTeam()) - tempBoard.getTeamValue(Helper.getOppositeTeam(getTeam()));
            if (tempBoard.isInCheckmate(Helper.getOppositeTeam(getTeam())))
            {
                ArrayList<Move> checkmate = new ArrayList<Move>();
                checkmate.add(m);
                return checkmate;
            }
            else if (value > maxBoardValue)
            {
                bestMoves.clear();
                bestMoves.add(m);
                maxBoardValue = value;
            }
            else if (value == maxBoardValue) bestMoves.add(m);
        }
        if (bestMoves.isEmpty()) bestMoves = allMoves;
        
        return bestMoves;
    }
}