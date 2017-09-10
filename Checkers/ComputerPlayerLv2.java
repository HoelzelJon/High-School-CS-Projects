import java.util.ArrayList;

/**
 * Better than level 1. can tell if a move it makes will get it jumped
 * 
 * @author Hoelzel
 * @version 3/28/2015
 */

public class ComputerPlayerLv2 implements ComputerPlayer
{
    private Team team;
    private CheckersBoard board;
    
    public ComputerPlayerLv2(CheckersBoard b, Team t)
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
        
        // sort through & find valid moves & jump moves
        for (int i = myPieces.size() - 1; i >= 0; i --)
        {
            OrderedPair spot = myPieces.get(i);
            if (board.hasMoreJumpMoves(spot.getX(), spot.getY())) piecesThatCanJump.add(spot);
            if (!board.pieceCanMove(spot.getX(), spot.getY())) myPieces.remove(spot);
        }
        
        ArrayList<Move> movesList = new ArrayList<Move>();
        boolean jumpMove = false;
        
        //fill up movesList will all available moves
        if (!piecesThatCanJump.isEmpty())
        {
            jumpMove = true;
            for (OrderedPair spot : piecesThatCanJump)
            {
                ArrayList<OrderedPair> placesToJumpTo = board.getPlacesToJumpTo(spot.getX(), spot.getY());
                
                for (OrderedPair newSpot : placesToJumpTo)
                {
                    movesList.add(new Move(spot, newSpot));
                }
            }
        }
        else
        {
            for (OrderedPair spot : myPieces)
            {
                ArrayList<OrderedPair> placesToMoveTo = board.getPlacesToMoveTo(spot.getX(), spot.getY());
                
                for (OrderedPair newSpot : placesToMoveTo)
                {
                    movesList.add(new Move(spot, newSpot));
                }
            }
        }
        
        ArrayList<Move> backupList = (ArrayList<Move>)movesList.clone();
        // Find which moves could get jumped and remove them
        for (int i = movesList.size() - 1; i >= 0; i --)
        {
            Move m = movesList.get(i);
            OrderedPair newSpot = m.getNewSpot();
            CheckersBoard tempBoard = board.clone();
            if (jumpMove) tempBoard.jumpMove(m);
            else tempBoard.movePiece(m);
            
            if (tempBoard.canGetJumped(newSpot.getX(), newSpot.getY())) movesList.remove(m);
        }
        
        if (movesList.isEmpty()) movesList = backupList;
        
        int rand = (int)(Math.random() * movesList.size());
        return movesList.get(rand);
    }
    
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
