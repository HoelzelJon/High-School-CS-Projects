import java.util.ArrayList;

/**
 * A better computer player
 * 
 * @author Hoelzel
 * @version 5/2/2015
 */

public class CompLv4 extends ComputerPlayer
{
    public CompLv4(Board b, Team t)
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
        
        ArrayList<Move> sorted = new ArrayList<Move>();
        for (Move m : allMoves)
        {
            Board tempBoard = getBoard().clone();
            tempBoard.movePiece(m);
            CompLv4 person = new CompLv4(tempBoard, Helper.getOppositeTeam(getTeam()));
            
            if (tempBoard.isInCheckmate(Helper.getOppositeTeam(getTeam())))
            {
                ArrayList<Move> checkmate = new ArrayList<Move>();
                checkmate.add(m);
                return checkmate;
            }
            
            tempBoard.spinBoard();
            
            if (!person.canMakeCheckmateMove())
            {
                sorted.add(m);
            }
        }
        if (sorted.isEmpty()) sorted = allMoves;
        
        ArrayList<Move> nonStalemateMoves = new ArrayList<Move>();
        for (Move m: sorted)
        {
            Board tempBoard = getBoard().clone();
            tempBoard.movePiece(m);
            tempBoard.spinBoard();
            if (!tempBoard.isInStalemate(Helper.getOppositeTeam(getTeam()))) nonStalemateMoves.add(m);
        }
        if (nonStalemateMoves.isEmpty()) nonStalemateMoves = sorted;
        
        ArrayList<Move> bestMoves = new ArrayList<Move>();
        int maxBoardValue = Integer.MIN_VALUE;
        for (Move m : nonStalemateMoves)
        {
            Board tempBoard = getBoard().clone();
            tempBoard.movePiece(m);
            int value = tempBoard.getBoardValue(getTeam()) - tempBoard.getTeamValue(Helper.getOppositeTeam(getTeam()));
            
            if (value > maxBoardValue)
            {
                bestMoves.clear();
                bestMoves.add(m);
                maxBoardValue = value;
            }
            else if (value == maxBoardValue) bestMoves.add(m);
        }
        if (bestMoves.isEmpty()) bestMoves = allMoves;
        
        ArrayList<Move> lowRiskMoves = new ArrayList<Move>();
        int minOtherTeamValue = Integer.MAX_VALUE;
        
        for (Move m : bestMoves)
        {
            Board tempBoard = getBoard().clone();
            tempBoard.movePiece(m);
            tempBoard.spinBoard();
            int value = tempBoard.getBoardValue(Helper.getOppositeTeam(getTeam()));
            tempBoard.spinBoard();
            
            if (value < minOtherTeamValue)
            {
                lowRiskMoves.clear();
                lowRiskMoves.add(m);
                minOtherTeamValue = value;
            }
            else if (value == minOtherTeamValue) lowRiskMoves.add(m);
        }
        if (lowRiskMoves.isEmpty()) lowRiskMoves = bestMoves;
        
        ArrayList<Move> checkMoves = new ArrayList<Move>();

        for (Move m : lowRiskMoves)
        {
            Board tempBoard = getBoard().clone();
            tempBoard.movePiece(m);
            tempBoard.spinBoard();
            if (tempBoard.isInCheck(Helper.getOppositeTeam(getTeam()))) checkMoves.add(m);
            tempBoard.spinBoard();
        }
        if (checkMoves.isEmpty()) checkMoves = lowRiskMoves;
        
        ArrayList<Move> pawnMoves = new ArrayList<Move>();
        boolean hasTwoSquarePawnMoves = false;
        
        for (Move m : checkMoves)
        {
            if (getBoard().getPiece(m.getOldSpot()) instanceof Pawn)
            {
                if (Math.abs(m.getOldSpot().getY() - m.getNewSpot().getY()) == 2 && !hasTwoSquarePawnMoves)
                {
                    pawnMoves.clear();
                    pawnMoves.add(m);
                    hasTwoSquarePawnMoves = true;
                }
                else if (!hasTwoSquarePawnMoves) pawnMoves.add(m);
                else if (Math.abs(m.getOldSpot().getY() - m.getNewSpot().getY()) == 2) pawnMoves.add(m);
            }
        }
        
        if (pawnMoves.isEmpty()) pawnMoves = checkMoves;
        
        ArrayList<Move> centerMoves = new ArrayList<Move>();
        double smallestDistanceFromCenter = 100;
            
        for (Move m : pawnMoves)
        {
            double distanceFromCenter = Math.abs(m.getNewSpot().getX() - 3.5);
            
            if (Helper.isLarger(smallestDistanceFromCenter, distanceFromCenter)) 
            {
                centerMoves.clear();
                centerMoves.add(m);
                smallestDistanceFromCenter = distanceFromCenter;
            }
            else if (Helper.approxEquals(smallestDistanceFromCenter, distanceFromCenter)) centerMoves.add(m);
        }
        
        return centerMoves;
    }
    
    private boolean canMakeCheckmateMove()
    {
        ArrayList<Move> allMoves = getBoard().getAllNonCheckMoves(getTeam());
        
        for (Move m : allMoves)
        {
            Board tempBoard = getBoard().clone();
            tempBoard.movePiece(m);
            
            tempBoard.spinBoard();
            
            if (tempBoard.isInCheckmate(Helper.getOppositeTeam(getTeam()))) return true;
        }
        
        return false;
    }
}