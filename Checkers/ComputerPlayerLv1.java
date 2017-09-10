import java.util.ArrayList;

/**
 * Make the computer play checkers
 * 
 * @author Hoelzel
 * @version 3/27/2015
 */

public class ComputerPlayerLv1 implements ComputerPlayer
{
    private Team team;
    private CheckersBoard board;
    
    public ComputerPlayerLv1(CheckersBoard b, Team t)
    {
        board = b;
        team = t;
    }
    
    public boolean willJump()
    {
        ArrayList<OrderedPair> myPieces = board.getAllPieces(team);
        ArrayList<OrderedPair> piecesThatCanJump = new ArrayList<OrderedPair>();
        
        for (int i = myPieces.size() - 1; i >= 0; i --)
        {
            OrderedPair spot = myPieces.get(i);
            if (board.hasMoreJumpMoves(spot.getX(), spot.getY())) piecesThatCanJump.add(spot);
            if (!board.pieceCanMove(spot.getX(), spot.getY())) myPieces.remove(spot);
        }
        
        if (piecesThatCanJump.isEmpty()) return false;
        else return true;
    }
    
    public boolean willJump(int x, int y)
    {
        return board.hasMoreJumpMoves(x, y);
    }
    
    public Move getNextMove()
    {
        ArrayList<OrderedPair> myPieces = board.getAllPieces(team);
        ArrayList<OrderedPair> piecesThatCanJump = new ArrayList<OrderedPair>();
        
        for (int i = myPieces.size() - 1; i >= 0; i --)
        {
            OrderedPair spot = myPieces.get(i);
            if (board.hasMoreJumpMoves(spot.getX(), spot.getY())) piecesThatCanJump.add(spot);
            if (!board.pieceCanMove(spot.getX(), spot.getY())) myPieces.remove(spot);
        }
        
        if (!piecesThatCanJump.isEmpty())
        {
            int pieceNum = (int)(Math.random() * piecesThatCanJump.size());
            OrderedPair pieceToMove = piecesThatCanJump.get(pieceNum);
            ArrayList<OrderedPair> placesToJumpTo = board.getPlacesToJumpTo(pieceToMove.getX(), pieceToMove.getY());
            int jumpNum = (int)(Math.random() * placesToJumpTo.size());
            return new Move(pieceToMove, placesToJumpTo.get(jumpNum));
        }
        else
        {
            int pieceNum = (int)(Math.random() * myPieces.size());
            OrderedPair pieceToMove = myPieces.get(pieceNum);
            ArrayList<OrderedPair> placesToMoveTo = board.getPlacesToMoveTo(pieceToMove.getX(), pieceToMove.getY());
            int moveNum = (int)(Math.random() * placesToMoveTo.size());
            return new Move(pieceToMove, placesToMoveTo.get(moveNum));
        }
    }
    
    /**
     * Only used when a second, third, etc., jump is being made
     */
    public Move getNextMove(int x, int y)
    {
        ArrayList<OrderedPair> myPieces = board.getAllPieces(team);
        OrderedPair pieceToMove = new OrderedPair(-1, -1);
        for (OrderedPair spot : myPieces)
        {
            if (spot.getX() == x && spot.getY() == y) pieceToMove = spot;
        }
        
        ArrayList<OrderedPair> placesToJumpTo = board.getPlacesToJumpTo(pieceToMove.getX(), pieceToMove.getY());
        int jumpNum = (int)(Math.random() * placesToJumpTo.size());
        return new Move(pieceToMove, placesToJumpTo.get(jumpNum));
    }
}