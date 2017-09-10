import java.util.ArrayList;

/**
 * Better than level 3. should be able to:
 * avoid double jumps even more than single jumps
 * prefer trades over just being jumped
 * get into position to jump other player when possible
 * 
 * @author Hoelzel
 * @version 3/28/2015
 */

public class ComputerPlayerLv4 implements ComputerPlayer
{
    private Team team;
    private CheckersBoard board;
    
    public ComputerPlayerLv4(CheckersBoard b, Team t)
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
        ArrayList<OrderedPair> piecesThatCanMove = new ArrayList<OrderedPair>();
        
        // sort through & find valid moves & jump moves
        for (OrderedPair spot : myPieces)
        {
            if (board.hasMoreJumpMoves(spot.getX(), spot.getY())) piecesThatCanJump.add(spot);
            if (board.pieceCanMove(spot.getX(), spot.getY())) piecesThatCanMove.add(spot);
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
            for (OrderedPair spot : piecesThatCanMove)
            {
                ArrayList<OrderedPair> placesToMoveTo = board.getPlacesToMoveTo(spot.getX(), spot.getY());
                
                for (OrderedPair newSpot : placesToMoveTo)
                {
                    movesList.add(new Move(spot, newSpot));
                }
            }
        }
        
        ArrayList<Move> leastJumpPossibilities = new ArrayList<Move>();
        int minJumps = 1000; // just a big number so that something will be under it.
        
        // find moves that make the least  jump possibilities for the other player
        for (Move m : movesList)
        {
            CheckersBoard tempBoard = board.clone();
            
            if (jumpMove) tempBoard.jumpMove(m);
            else tempBoard.movePiece(m);
            
            // Find how many ways it could be jumped if it makes the move
            int jumpsAfterCounter = 0;
            ArrayList<OrderedPair> allMyPieces = tempBoard.getAllPieces(team);
            ArrayList<Move> waysToJumpMe = new ArrayList<Move>();
            for (OrderedPair spot : allMyPieces)
            {
                if (tempBoard.canGetJumped(spot.getX(), spot.getY()))
                {
                    if (tempBoard.isAKing(spot.getX(), spot.getY())) jumpsAfterCounter += 2;
                    else jumpsAfterCounter ++;
                    waysToJumpMe.addAll(tempBoard.getWaysToJump(spot.getX(), spot.getY()));
                }
            }
            
            // check for double jumps
            for (Move firstJump : waysToJumpMe)
            {
                CheckersBoard tempBoard2 = tempBoard.clone();
                firstJump.spinBoard();
                tempBoard2.spinBoard();
                tempBoard2.jumpMove(firstJump);
                
                OrderedPair newPersonsSpot = firstJump.getNewSpot();
                ComputerPlayerLv1 player = new ComputerPlayerLv1(tempBoard2, team);
                
                if (player.willJump(newPersonsSpot.getX(), newPersonsSpot.getY())) jumpsAfterCounter ++;
                
                firstJump.spinBoard();
            }
            
            if (jumpsAfterCounter < minJumps)
            {
                leastJumpPossibilities.clear();
                leastJumpPossibilities.add(m);
                minJumps = jumpsAfterCounter;
            }
            else if (jumpsAfterCounter == minJumps)
            {
                leastJumpPossibilities.add(m);
            }
        }
        
        if (leastJumpPossibilities.isEmpty()) leastJumpPossibilities = movesList;
        
        /**
         * Add here - if piece isn't a king and the two pieces "above" it are in row 8, then move one of those kings if possible
         */
        
        //should check to see if the piece being moved is just moving from being jumped one way to being jumped another way
        ArrayList<Move> notPointless = new ArrayList<Move>();
        for (Move m : leastJumpPossibilities)
        {
            CheckersBoard tempBoard = board.clone();
            OrderedPair oldSpot = m.getOldSpot();
            OrderedPair newSpot = m.getNewSpot();
            
            boolean wouldGetJumpedBefore = tempBoard.canGetJumped(oldSpot.getX(), oldSpot.getY());
            
            if (jumpMove) tempBoard.jumpMove(m);
            else tempBoard.movePiece(m);
            
            boolean willGetJumpedAfter = tempBoard.canGetJumped(newSpot.getX(), newSpot.getY());
            
            if (!(wouldGetJumpedBefore && willGetJumpedAfter)) notPointless.add(m);
        }
        
        if (notPointless.isEmpty()) notPointless = leastJumpPossibilities;
        
        ArrayList<Move> movesIntoJumpPositions = new ArrayList<Move>();
        for (Move m : notPointless)
        {
            CheckersBoard tempBoard = board.clone();
            if (jumpMove) tempBoard.jumpMove(m);
            else tempBoard.movePiece(m);
            
            OrderedPair spot = m.getNewSpot();
            if (tempBoard.hasMoreJumpMoves(spot.getX(), spot.getY())) movesIntoJumpPositions.add(m);
        }
        
        if (movesIntoJumpPositions.isEmpty()) movesIntoJumpPositions = notPointless;
        
        // sort moves by closeness to becoming king
        ArrayList<Move> closestToKing = new ArrayList<Move>();
        int smallestYDestination = 8;
        
        for (Move m : movesIntoJumpPositions)
        {
            OrderedPair oldSpot = m.getOldSpot();
            int y = m.getNewSpot().getY();
            if (y < smallestYDestination && (!board.isAKing(oldSpot.getX(), oldSpot.getY())))
            {
                closestToKing.clear();
                closestToKing.add(m);
                smallestYDestination = y;
            }
            else if (y == smallestYDestination && (!board.isAKing(oldSpot.getX(), oldSpot.getY()))) closestToKing.add(m);
        }
        
        if (closestToKing.isEmpty()) closestToKing = movesIntoJumpPositions;
        
        int rand = (int)(Math.random() * closestToKing.size());
        return closestToKing.get(rand);
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
        ArrayList<Move> movesList = new ArrayList<Move>();
        
        for (OrderedPair spot : placesToJumpTo)
        {
            movesList.add(new Move(pieceToMove, spot));
        }
        
        ArrayList<Move> leastJumpPossibilities = new ArrayList<Move>();
        int minJumps = 1000; // just a big number so that something will be under it.
        
        // find moves that make the least  jump possibilities for the other player
        for (Move m : movesList)
        {
            CheckersBoard tempBoard = board.clone();
            
            tempBoard.jumpMove(m);
            
            // Find how many ways it could be jumped if it makes the move
            int jumpsAfterCounter = 0;
            ArrayList<OrderedPair> allMyPieces = tempBoard.getAllPieces(team);
            ArrayList<Move> waysToJumpMe = new ArrayList<Move>();
            for (OrderedPair spot : allMyPieces)
            {
                if (tempBoard.canGetJumped(spot.getX(), spot.getY()))
                {
                    if (tempBoard.isAKing(spot.getX(), spot.getY())) jumpsAfterCounter += 2;
                    else jumpsAfterCounter ++;
                    waysToJumpMe.addAll(tempBoard.getWaysToJump(spot.getX(), spot.getY()));
                }
            }
            
            // check for double jumps
            for (Move firstJump : waysToJumpMe)
            {
                CheckersBoard tempBoard2 = tempBoard.clone();
                firstJump.spinBoard();
                tempBoard2.spinBoard();
                tempBoard2.jumpMove(firstJump);
                
                OrderedPair newPersonsSpot = firstJump.getNewSpot();
                ComputerPlayerLv1 player = new ComputerPlayerLv1(tempBoard2, team);
                
                if (player.willJump(newPersonsSpot.getX(), newPersonsSpot.getY())) jumpsAfterCounter ++;
                
                firstJump.spinBoard();
            }
            
            if (jumpsAfterCounter < minJumps)
            {
                leastJumpPossibilities.clear();
                leastJumpPossibilities.add(m);
                minJumps = jumpsAfterCounter;
            }
            else if (jumpsAfterCounter == minJumps)
            {
                leastJumpPossibilities.add(m);
            }
        }
        
        if (leastJumpPossibilities.isEmpty()) leastJumpPossibilities = movesList;
        
        //should check to see if the piece being moved is just moving from being jumped one way to being jumped another way
        ArrayList<Move> notPointless = new ArrayList<Move>();
        for (Move m : leastJumpPossibilities)
        {
            CheckersBoard tempBoard = board.clone();
            OrderedPair oldSpot = m.getOldSpot();
            OrderedPair newSpot = m.getNewSpot();
            
            boolean wouldGetJumpedBefore = tempBoard.canGetJumped(oldSpot.getX(), oldSpot.getY());
            tempBoard.jumpMove(m);
            boolean willGetJumpedAfter = tempBoard.canGetJumped(newSpot.getX(), newSpot.getY());
            
            if (!(wouldGetJumpedBefore && willGetJumpedAfter)) notPointless.add(m);
        }
        
        if (notPointless.isEmpty()) notPointless = leastJumpPossibilities;

        ArrayList<Move> movesIntoJumpPositions = new ArrayList<Move>();
        for (Move m : notPointless)
        {
            CheckersBoard tempBoard = board.clone();
            tempBoard.jumpMove(m);
            
            OrderedPair spot = m.getNewSpot();
            if (tempBoard.hasMoreJumpMoves(spot.getX(), spot.getY())) movesIntoJumpPositions.add(m);
        }
        
        if (movesIntoJumpPositions.isEmpty()) movesIntoJumpPositions = notPointless;
        
        
        int rand = (int)(Math.random() * movesIntoJumpPositions.size());
        return movesIntoJumpPositions.get(rand);
    }
}