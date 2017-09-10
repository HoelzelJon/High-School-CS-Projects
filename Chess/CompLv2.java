import java.util.ArrayList;

/**
 * A better computer player. takes a piece if it can. if not, it it gets the move with the best boardValue
 * 
 * @author Hoelzel
 * @version 4/28/2015
 */

public class CompLv2 extends ComputerPlayer
{
    public CompLv2(Board b, Team t)
    {
        super.setTeam(t);
        super.setBoard(b);
    }
        
    public Move nextMove()
    {
        ArrayList<Move> allMoves = getBoard().getAllNonCheckMoves(getTeam());
        
        ArrayList<Move> takingMoves = new ArrayList<Move>();
        for (Move m : allMoves)
        {
            if (getBoard().hasPieceOfOtherTeam(m.getNewSpot(), getTeam())) takingMoves.add(m);
        }
        if (takingMoves.isEmpty()) takingMoves = allMoves;
        
        ArrayList<Move> sortedMoves = new ArrayList<Move>();
        int maxBoardValue = Integer.MAX_VALUE;
        for (Move m : takingMoves)
        {
            Board tempBoard = getBoard().clone();
            tempBoard.movePiece(m);
            int value = tempBoard.getBoardValue(getTeam());
            if (value >= maxBoardValue)
            {
                sortedMoves.add(m);
                maxBoardValue = value;
            }
        }
        if (sortedMoves.isEmpty()) sortedMoves = takingMoves;
        
        return sortedMoves.get((int)(Math.random() * sortedMoves.size()));
    }
    
    public Piece pieceToChangePawnTo()
    {
        return new Queen(getTeam());
    }
}
