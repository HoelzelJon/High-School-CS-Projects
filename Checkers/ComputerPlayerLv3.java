import java.util.ArrayList;

/**
 * Better than level 2. can avoid getting jumped and tries to move towards getting Kings
 * 
 * @author Hoelzel
 * @version 3/28/2015
 */

public class ComputerPlayerLv3 implements ComputerPlayer
{
    private Team team;
    private CheckersBoard board;
    
    public ComputerPlayerLv3(CheckersBoard b, Team t)
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
        
        ArrayList<Move> leastJumpPossibilities = new ArrayList<Move>();
        int minJumps = 100; // just a big number so that something will be under it.
        
        // find moves that make the least 
        for (Move m : movesList)
        {
            CheckersBoard tempBoard = board.clone();
            if (jumpMove) tempBoard.jumpMove(m);
            else tempBoard.movePiece(m);
            
            int jumpsAfterCounter = 0;
            ArrayList<OrderedPair> allMyPieces = tempBoard.getAllPieces(team);
            for (OrderedPair spot : allMyPieces)
            {
                if (tempBoard.canGetJumped(spot.getX(), spot.getY())) jumpsAfterCounter ++;
            }
            
            if (jumpsAfterCounter < minJumps)
            {
                leastJumpPossibilities.clear();
                leastJumpPossibilities.add(m);
                minJumps = jumpsAfterCounter;
            }
            else if (jumpsAfterCounter == minJumps) leastJumpPossibilities.add(m);
        }
        
        // sort moves by closeness to becoming king
        ArrayList<Move> closestToKing = new ArrayList<Move>();
        int smallestYDestination = 8;
        
        for (Move m : leastJumpPossibilities)
        {
            OrderedPair oldSpot = m.getNewSpot();
            int y = m.getNewSpot().getY();
            if (y < smallestYDestination && (!board.isAKing(oldSpot.getX(), oldSpot.getY())))
            {
                closestToKing.clear();
                closestToKing.add(m);
                smallestYDestination = y;
            }
            else if (y == smallestYDestination && (!board.isAKing(oldSpot.getX(), oldSpot.getY()))) closestToKing.add(m);
        }
        if (closestToKing.size() == 0)
        {
            int rand = (int)(Math.random() * leastJumpPossibilities.size());
            return leastJumpPossibilities.get(rand);
        }
        else
        {
            int rand = (int)(Math.random() * closestToKing.size());
            return closestToKing.get(rand);
        }
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
        ArrayList<Move> myMoves = new ArrayList<Move>();
        for (OrderedPair spot : placesToJumpTo)
        {
            myMoves.add(new Move(pieceToMove, spot));
        }
        
        ArrayList<Move> leastJumpPossibilities = new ArrayList<Move>();
        int minJumps = 100; // just a big number so that something will be under it.
        
        // find moves that make the least jump possibilities for the other player
        for (Move m : myMoves)
        {
            CheckersBoard tempBoard = board.clone();
            tempBoard.jumpMove(m);
            
            int jumpsAfterCounter = 0;
            ArrayList<OrderedPair> allMyPieces = tempBoard.getAllPieces(team);
            for (OrderedPair spot : allMyPieces)
            {
                if (tempBoard.canGetJumped(spot.getX(), spot.getY())) jumpsAfterCounter ++;
            }
            
            if (jumpsAfterCounter < minJumps)
            {
                leastJumpPossibilities.clear();
                leastJumpPossibilities.add(m);
                minJumps = jumpsAfterCounter;
            }
            else if (jumpsAfterCounter == minJumps) leastJumpPossibilities.add(m);
        }
        
        int rand = (int)(Math.random() * leastJumpPossibilities.size());
        return leastJumpPossibilities.get(rand);
    }
}